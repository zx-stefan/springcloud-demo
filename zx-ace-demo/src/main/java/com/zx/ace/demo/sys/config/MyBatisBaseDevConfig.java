package com.zx.ace.demo.sys.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zx.ace.demo.sys.config.datasource.DBContextHolder;
import com.zx.ace.demo.sys.config.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017/8/26 0026.
 * 配置数据源连接 开发环境
 */
@Configuration
@PropertySource("classpath:datasource_dev.properties")
@MapperScan(basePackages = "com.zx.ace.demo.**.dao")
@Profile("dev")
public class MyBatisBaseDevConfig implements EnvironmentAware{

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    /**
     * 创建TestDb数据源，此处datasource_dev.properties中数据库的账号和密码应该是加密处理的
     * 需要在此处解密进行连接，本程序没有进行此操作
     * @return
     * @throws Exception
     */
    @Bean
    public DataSource myTestDbDataSource() throws Exception{
        Properties props = new Properties();
        props.put("url", env.getProperty("jdbc1.url"));
        props.put("username", env.getProperty("jdbc1.username"));
        props.put("password", env.getProperty("jdbc1.password"));
        this.setCommonJDBCProperties(props);
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * 创建TestDb2数据源，此处datasource_dev.properties中数据库的账号和密码应该是加密处理的
     * 需要在此处解密进行连接，本程序没有进行此操作
     * @return
     * @throws Exception
     */
    @Bean
    public DataSource myTestDb2DataSource() throws Exception{
        Properties props = new Properties();
        props.put("url", env.getProperty("jdbc2.url"));
        props.put("username", env.getProperty("jdbc2.username"));
        props.put("password", env.getProperty("jdbc2.password"));
        this.setCommonJDBCProperties(props);
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@Autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入，（例如有多个DataSource类型的实例）
     * @param myTestDbDataSource
     * @param myTestDb2DataSource
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("myTestDbDataSource") DataSource myTestDbDataSource,
                                        @Qualifier("myTestDb2DataSource") DataSource myTestDb2DataSource){
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DBContextHolder.DATA_SOURCE_A, myTestDbDataSource);
        targetDataSource.put(DBContextHolder.DATA_SOURCE_B, myTestDb2DataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource); //该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(myTestDbDataSource); //默认的datasource设置为myTestDbDataSource
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("myTestDbDataSource") DataSource myTestDbDataSource,
                                                   @Qualifier("myTestDb2DataSource") DataSource myTestDb2DataSource){
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(this.dataSource(myTestDbDataSource, myTestDb2DataSource));
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:com/zx/ace/demo/**/dao/*.xml"));
            return bean.getObject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 配置事务管理
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 设置公用的jdbc属性
     * @param props
     */
    private void setCommonJDBCProperties(Properties props){
        props.put("driverClassName", env.getProperty("jdbc.driverClassName"));
        props.put("initialSize", env.getProperty("jdbc.initialSize"));
        props.put("minIdle", env.getProperty("jdbc.minIdle"));
        props.put("maxActive", env.getProperty("jdbc.maxActive"));
        props.put("maxWait", env.getProperty("jdbc.maxWait"));
        props.put("validationQuery", env.getProperty("jdbc.validationQuery"));
        props.put("testOnBorrow", env.getProperty("jdbc.testOnBorrow"));
        props.put("testOnReturn", env.getProperty("jdbc.testOnReturn"));
        props.put("testWhileIdle", env.getProperty("jdbc.testWhileIdle"));
        props.put("minEvictableIdleTimeMillis", env.getProperty("jdbc.minEvictableIdleTimeMillis"));
        props.put("timeBetweenEvictionRunsMillis", env.getProperty("jdbc.timeBetweenEvictionRunsMillis"));
        props.put("removeAbandoned", env.getProperty("jdbc.removeAbandoned"));
        props.put("removeAbandonedTimeout", env.getProperty("jdbc.removeAbandonedTimeout"));
        props.put("poolPreparedStatements", env.getProperty("jdbc.poolPreparedStatements"));
        props.put("maxPoolPreparedStatementsPerConnectionSize", env.getProperty("jdbc.maxPoolPreparedStatementsPerConnectionSize"));
        props.put("logAbandoned", env.getProperty("jdbc.logAbandoned"));
        props.put("filters", env.getProperty("jdbc.filters"));
    }

}
