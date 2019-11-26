package com.sun.dataaop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * spring aop 用于在进入service前根据service 方法上的注解设置使用数据源到本地线程变量中，退出service清空数据源设置
 */
@Aspect
@Order(1)//order 的顺序不能随便改，必须比 MyBatisConfig.EnableTransactionManagement 切面中的 order 小，目的是让dataaop先于事务切面执行
@Component
public class DataSourceAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("@annotation(com.sun.dataaop.Master)&& (execution(* com.sun..*service..*(..)))")
    public void writePointcut() {

    }

    @Pointcut("(@annotation(com.sun.dataaop.Slave))&& (execution(* com.sun.*service..*(..)))")
    public void readPointcut() {

    }

    @Pointcut("execution(* com.sun..*service..*(..))")
    public void clearPoint() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }

    @After("clearPoint()")
    public void clear() {
        DBContextHolder.clear();
    }
}
