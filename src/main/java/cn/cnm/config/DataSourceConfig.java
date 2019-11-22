package cn.cnm.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author lele
 * @version 1.0
 * @Description 数据源配置
 * @Email 414955507@qq.com
 * @date 2019/11/22 21:38
 */
@Configuration
public class DataSourceConfig {
    private static final String DATASOURCE_NAME = "dbDataSource";

    // 数据源配置的前缀，必须与application.properties中配置的对应数据源的前缀一致
    private static final String BUSINESS_DATASOURCE_PREFIX = "spring.datasource.druid.business";
    private static final String QUARTZ_DATASOURCE_PREFIX = "spring.datasource.druid.quartz";

    /* 主业务数据源 */
    @Primary
    @Bean(name = DATASOURCE_NAME)
    @ConfigurationProperties(prefix = BUSINESS_DATASOURCE_PREFIX)
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    /* @QuartzDataSource注解是配置Quartz独立数据源的配置 */
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = QUARTZ_DATASOURCE_PREFIX)
    public DataSource quartzDataSource() {
        return new DruidDataSource();
    }


    // 配置Mybatis环境, 母鸡到干什么的呀
    //    @Value("${mybatis.queryLimit}")
    //    private String queryLimit;
    //    // 日志对象实例
    //    private static final Logger logger = LogManager.getLogger(RabbitMqFastJsonConverter.class);
    //    private static final String SESSION_FACTORY = "dbSqlSessionFactory";
    //    // mapper类的包路径
    //    static final String MAPPER_PACKAGE = "cn.cnm.mapper";
    //    // 实体类路径
    //   private static final String MODEL_PACKAGE = "cn.cnm.pojo";
    //    // 自定义mapper的xml文件路径
    //   private static final String MAPPER_XML_PATH = "classpath*:cm.cnm.mapper/*Mapper.xml";

    //    @Primary
    //    @Bean(name = SESSION_FACTORY)
    //    public SqlSessionFactory sqlSessionFactory() {
    //        logger.info("配置SqlSessionFactory开始");
    //        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
    //        sessionFactoryBean.setDataSource(druidDataSource());
    //        try {
    //            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    //            // 自定义mapper的xml文件地址，当通用mapper提供的默认功能无法满足我们的需求时，可以自己添加实现，与mybatis写mapper一样
    //            sessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_XML_PATH));
    //            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    //            Properties properties = new Properties();
    //            properties.put("queryLimit", queryLimit);
    //            configuration.setVariables(properties);
    //            configuration.setMapUnderscoreToCamelCase(true);
    //            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
    //            sessionFactoryBean.setConfiguration(configuration);
    //            sessionFactoryBean.setTypeAliasesPackage(MODEL_PACKAGE);
    //            return sessionFactoryBean.getObject();
    //        } catch (Exception e) {
    //            logger.error("配置SqlSessionFactory失败，error:{}", e.getMessage());
    //            throw new RuntimeException(e.getMessage());
    //        }
    //    }
}
