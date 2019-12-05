package com.sun.dataaop;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//order 的顺序不能随便改，必须比 dataAop 切面中的 order 大，目的是让dataaop先于事务切面执行
@EnableTransactionManagement(order = 2)
@Configuration
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource myRoutingDataSource, MybatisProperties mybatisProperties) throws Exception {
       SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
        sqlSessionFactoryBean.setConfiguration( mybatisProperties.getConfiguration());
       sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
        return sqlSessionFactoryBean.getObject();
    }
    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource myRoutingDataSource) {
        return new DataSourceTransactionManager(myRoutingDataSource);
    }
}
