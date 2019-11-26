package com.sun.dataaop;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 主从数据源配置
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    public DataSource dataSource(DataSourceProperties properties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(properties.getUrl());
        datasource.setDriverClassName(properties.getDriverClassName());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setInitialSize(Integer.valueOf(properties.getInitialSize()));
//        datasource.setMinIdle(Integer.valueOf(properties.getMinIdle()));
        datasource.setMaxWait(Long.valueOf(properties.getMaxWait()));
        datasource.setMaxActive(Integer.valueOf(properties.getMaxActive()));
        datasource.setDefaultReadOnly(properties.getDefaultReadOnly());
//        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(properties.getMinEvictableIdleTimeMillis()));
        return datasource;
    }

    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSourceProperties") DataSourceProperties properties, @Qualifier("slaveDataSourceProperties") DataSourceProperties properties2) {
        DataSource masterDataSource = dataSource(properties);
        DataSource slave1DataSource = dataSource(properties2);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE, slave1DataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        //默认数据源为从库，根据业务需要设置
        myRoutingDataSource.setDefaultTargetDataSource(slave1DataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }
}

/**
 * 数据源参数配置
 */
class DataSourceProperties {

    private String type;
    private String url;
    private String username;

    private String driverClassName;

    private String initialSize;

    private String minIdle;

    private String maxWait;

    private String maxActive;

    private String minEvictableIdleTimeMillis;

    private String password;

    private boolean defaultReadOnly;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public boolean getDefaultReadOnly() {
        return defaultReadOnly;
    }

    public void setDefaultReadOnly(boolean defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
    }
}