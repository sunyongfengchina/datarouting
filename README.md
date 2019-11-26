# datarouting

#构建
  springboot + mybatis 
  
#功能介绍
    数据库读写分离实践
    
#主要实现逻辑
  通过 spring aop在进入service前根据执行service方法上的标识使用主库/从库的自定义注解设置数据源标识符到线程本地变量，并 重写AbstractRoutingDataSource 的determineCurrentLookupKey方法，此方法中从线程本地变量中读取数据库标识并返回。
  
  代码里aop是通过Master和slave注解标识使用的数据库的，读取不到注解时使用默认数据库（可指定）。
  根据代码风格，亦可以通过service方法前缀（get,select,update,add,delete..）进行匹配。
  上面俩种aop切面方式最好根据现有代码实际情况进行选择成本最低的。
  
  见到有用mybatis的拦截器 根据sql类型做拦截的，不需要修改现有代码。但是是存在问题的。mybatis的拦截器中处理数据库目前没有办法支持多条语句的事务处理。所以放弃了。
  
  如您发现代码中存在问题，还请提出以待修改。
