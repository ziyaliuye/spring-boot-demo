package cn.cnm;

import cn.cnm.mail.MailMessageService;
import cn.cnm.pojo.Flower;
import cn.cnm.pojo.Person;
import cn.cnm.service.HelloService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringBootDemoApplicationTests {
    @Autowired
    Person person;
    @Autowired
    HelloService helloService;

    @Test
    void contextLoads() {
        System.out.println(person);
    }

    @Test
    void testBean() {
        System.out.println(helloService);
    }

    /* 日志系统使用测试 */
    private Logger logger = LogManager.getLogger();

    @Test
    void logTest() {
        /* 日志的级别由低到高 */
        // trace输出日志
        logger.trace("trace logger message...");
        // debug输出日志
        logger.debug("debug logger message...");
        // info输出日志
        logger.info("info logger message...");
        // rrror输出日志
        logger.error("info logger message...");
    }

    /* 注入数据源即可使用 */
    @Autowired
    DataSource dataSource;
    /* SpringBoot提供的JdbcTemplate， 简化代码 */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testJDBC() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from flower");
        System.out.println(list);
    }

    /* redis操作示例 */
    // redis操作模板类， key value形式
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    // 因为redis String操作比较频繁， 所以Spring单独抽出一个String的模版类
            StringRedisTemplate stringRedisTemplate;
    /* 名字都叫RedisTemplate， 根据泛型来区别使用哪个模板类 */
    @Autowired
    RedisTemplate<Object, Map<String, Object>> mapRedisTemplate;

    @Test
    void redisTest() {
        /*  操作方法：opsXxxx().xxx  ops是指操作的数据对应的类型， 第二个xxx才是操作的方式 */
        // 将String类型的msg追加"redis.."
        stringRedisTemplate.opsForValue().append("msg", "redis..");
        System.out.println("msg：" + stringRedisTemplate.opsForValue().get("msg"));
        stringRedisTemplate.opsForList().leftPush("mylist", "7");
        stringRedisTemplate.opsForList().leftPop("mylist");
        System.out.println("mylist" + stringRedisTemplate.opsForList().range("mylist", 0, 6));

        // 保存一个对象（注意对象以及对象的属性对象都需要实现序列化）， 通常保存为json串， 不需要实现序列户接口
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from flower");
        for (Map<String, Object> m : list) {
            mapRedisTemplate.opsForValue().set("flower" + m.get("id"), m);
        }
    }

    // @Qualifier指定获取容器指定ID的Bean
    @Qualifier("flowerCacheManager")
    @Autowired
    RedisCacheManager flowerCacheManager;

    @Test
    public void cacheManagerTest() {
        // 直接拉取指定缓存进行操作, 不存在就会新建
        Cache flower = flowerCacheManager.getCache("flower");
        // 相当于新建了一个key-value
        flower.put("heihei", "wocao");
        System.out.println(flower.toString());
    }

    /* 操作RabbitMQ */
    // RabbitTemplate用于操作RabbitMQ， 发送、消费消息等操作
    @Autowired
    RabbitTemplate rabbitTemplate;

    // 单播（点对点）消息发送测试
    @Test
    public void rabbitDirectSend() {
        // 第一个参数：交换器 第二个参数：路由键 第三个参数：要发送的消息（需要自己构建消息对象）
        // rabbitTemplate.send("", "", Message);
        Map<String, Object> map = new HashMap<>();
        map.put("msg1", "Hello first AMQP...");
        map.put("msg2", "you are da shabi");
        // 第一个参数：交换器 第二个参数：路由键 第三个参数：要发送的对象， 会自动序列化并当成消息体发送
        rabbitTemplate.convertAndSend("exchange.direct", "cn.news", map);
    }

    // 单播（点对点）消息接收测试
    @Test
    public void rabbitDirectReceive() {
        // 接收指定队列的消息， 只接收消息头， 没有消息体
        // rabbitTemplate.receive();
        // 接收消息体并转换需要的对象
        Object obj = rabbitTemplate.receiveAndConvert("cn.news");
        System.out.println(obj);
    }

    // 监听测试
    @Test
    public void rabbitListenerTest() {
        Flower flower = new Flower(1, "菊花", 8.8F, "哈哈", 1);
        // 第一个参数：交换器 第二个参数：路由键 第三个参数：要发送的对象， 会自动序列化并当成消息体发送
        rabbitTemplate.convertAndSend("exchange.direct", "cn.news", flower);
    }

    /* AmqpAdmin用于管理RabbitMQ组件， 用于创建队列、交换器等操作 */
    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void createAmqpParts() {
        // Exchange是一个接口， 实例化它的实现类， 对应的就是RabbitMQ中的三种Exchange
        // 参数还可以指定是否持久化, 默认就是， 这里先省略
        Exchange exchange = new DirectExchange("amqpadmin.exchange");
        /* 凡是以declare开头的方法就是创建组件, remove开头的方法就是删除组件 */
        amqpAdmin.declareExchange(exchange);
        System.out.println("交换器：amqpadmin.exchange 创建完成...");

        // Queue是一个类，就是队列的意思，使用空参构造器会随机取一个队列名字
        Queue queue = new Queue("amqpadmin.queue");
        amqpAdmin.declareQueue(queue);
        System.out.println("队列：amqpadmin.queue 创建完成...");

        /*
         * 创建绑定规则， 对应的类就是Binding
         *  第一个参数：目的地名字
         *  第二个参数：目的地类型， 绑定交换器还是队列, 这里一般就选队列
         *  第三个参数：交换器的名字
         *  第四个参数：路由键, 是精准匹配还是模糊取决于交换器类型
         *  第五个参数：参数头信息， Map类型
         */
        Binding binding = new Binding(
                "amqpadmin.queue", Binding.DestinationType.QUEUE,
                "amqpadmin.exchange", "amqp.wocao", null);
        amqpAdmin.declareBinding(binding);
        System.out.println("交换器队列绑定成功....");

        /* 对应的删除操作与创建流程一致 */
    }

    /* Jest客户端操作ES */
    @Autowired
    JestClient jestClient;

    // 将对象构建为索引（存储到ES中）
    @Test
    public void jsetIndexBuilder() {
        // 准备需要保存的数据（索引）
        Flower flower = new Flower(1, "菊花", 8.8F, "哈哈", 1);
        /*
         * Index类构建一个索引功能， 指定具体的索引信息
         *   Builder需要构建的对象
         *   index索引的名字
         *   index索引的类型
         *   build()构建
         */
        Index index = new Index.Builder(flower).index("cnm").type("flower").build();
        // 使用JestClient保存对象
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            System.out.println("索引构建失败...");
            e.printStackTrace();
        }
    }

    // 搜索
    @Test
    public void jsetIndexQuery() {
        //查询表达式
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"name\" : \"牵牛花\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        //构建搜索功能
        Search search = new Search.Builder(json).addIndex("cnm").addType("flower").build();
        //执行
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            System.out.println("查询失败...");
            e.printStackTrace();
        }
    }

    /* SpringData操作ES（测试无效， 先用Jest顶着， 后期更正） begin */
    @Qualifier(value = "restHighLevelClient")
    @Autowired
    RestHighLevelClient restHighLevelClient;

    // 自定义一个接口集成ElasticsearchRepository接口， 然后直接使用它父接口中的方法
    @Autowired
    // FlowerRepository flowerRepository;

    // 添加索引
    // @Test
    public void elasticsearchRepositoryIndex() {
        // 实体类上必须注解@Document指定存储名称和索引类型
        Flower flower = new Flower(1, "菊花", 8.8F, "哈哈", 1);
        // 索引（存储）这个对象
        // flowerRepository.save(flower);
    }

    // 添加索引
    // @Test
    public void elasticsearchRepositorySearch() {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "");
        // 索引（存储）这个对象
        // flowerRepository.search(queryBuilder);
    }

    // @Test
    public void test1() {
        //index名必须全小写，否则报错
        String index = "flower";
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            CreateIndexResponse indexResponse = restHighLevelClient.indices().create(request);
            if (indexResponse.isAcknowledged()) {
                System.out.println("创建索引成功");

            } else {
                System.out.println("创建索引失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* SpringData操作ES（测试无效， 先用Jest顶着， 后期更正） end */

    /* 发送邮件 */
    @Autowired
    MailMessageService mailMessageService;
    // 收件人
    @Value("${touser}")
    String touser;
    // 抄送人
    @Value("${copyuser}")
    String copyuser;

    // 发送文字邮件
    @Test
    public void sendMail() {
        // 主题
        String topic = "在吗？";
        // 内容
        String text = "吃了没， 没吃就多吃点...";
        // 收件人
        String[] tousers = touser.split(",");
        mailMessageService.sendSimple(topic, text, tousers);
    }

    // 发送带附件邮件
    @Test
    public void sendFileMail() {
        // 主题
        String topic = "在吗？";
        // 内容
        String text = "吃了没， 没吃就多吃点...";
        // 收件人
        String[] tousers = touser.split(",");
    }
}
