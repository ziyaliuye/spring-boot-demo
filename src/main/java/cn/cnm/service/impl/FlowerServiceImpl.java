package cn.cnm.service.impl;

import cn.cnm.mapper.FlowerMapper;
import cn.cnm.pojo.Flower;
import cn.cnm.service.FlowerService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/21 11:53
 */
@Service("flowerService")
/* 开启缓存， value： */
/*
 * @Cacheable 为当前方法开启缓存
 *  cacheNames/value 指定缓存组件的名字， 两个属性作用一样（新版本不指定名称会报错）
 *  key 指定缓存数据时使用的key（可以使用SpEl表达式）， 不指定默认使用方法名+参数值作为key
 *      SpEL示例：#id 参数ID的值  #a0 #p0 #root.args[0]， 详细参考文档
 *  keyGenerator：key的生成器， 可以自己指定key的生成器组件ID
 *      keyGenerator和key参数只能二选一
 *  cacheManager 指定缓存管理器
 *  cacheResolver 指定缓存解析器， 和cacheManager效果一样， 只能二选一
 *  condition 指定符合条件的情况下才缓存， 也可以使用SpEl表达式
 *  unless 否定缓存， 和condition作用相反
 *  sync 是否使用异步模式
 *
 *   原理：
 *   1、自动配置类；CacheAutoConfiguration
 *   2、缓存的配置类
 *   org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
 *   org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
 *   org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
 *   3、哪个配置类默认生效：SimpleCacheConfiguration；
 *
 *   4、给容器中注册了一个CacheManager：ConcurrentMapCacheManager
 *   5、可以获取和创建ConcurrentMapCache类型的缓存组件；他的作用将数据保存在ConcurrentMap中；
 *
 *   运行流程：
 *   @Cacheable：
 *   1、方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取；
 *      （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建。
 *   2、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
 *      key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
 *          SimpleKeyGenerator生成key的默认策略；
 *                  如果没有参数；key=new SimpleKey()；
 *                  如果有一个参数：key=参数的值
 *                  如果有多个参数：key=new SimpleKey(params)；
 *   3、没有查到缓存就调用目标方法；
 *   4、将目标方法返回的结果，放进缓存中
 *
 *   @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
 *   如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
 *
 *   核心：
 *      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
 *      2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
 */
public class FlowerServiceImpl implements FlowerService {
    @Resource
    private FlowerMapper flowerMapper;

    @Override
    /* @Cacheable 为方法加上缓存 */
    // @Cacheable(cacheNames = "flower", key = "#root.args[0]", condition = "#id<5")
    // @Cacheable(cacheNames = "flower", keyGenerator = "myKeyGenerator", condition = "#id<5")
    @Cacheable(cacheNames = "flower", key = "#id", condition = "#id<5")
    public Flower selectById(Integer id) {
        return flowerMapper.selectById(id);
    }

    @Override
    /*
     * @CachePut 一般用于更新操作， 先执行更新的操作， 然后将更新的数据存入缓存中
     *  要确保更新的数据存的key和查询的一致才会生效， 不然每次存的和查询的不一致相当于没用
     *  key可以使用#result， 但是@Cacheable中无法使用#result
     *      因为@Cacheable是在操作之前进行缓存检查，所以#result压根没东西， 而@CachePut是在操作之后
     */
    @CachePut(value = "flower", key = "#id", condition = "#id<5")
    public void updateById(Integer id) {
        flowerMapper.updateById(id);
    }

    @Override
    public void deleteById(Integer id) {
        // flowerMapper.deleteById(id);
    }
}
