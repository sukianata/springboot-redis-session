# springboot-redis-session

该项目与另一个项目[spring-session-redis-demo](https://github.com/sukianata/spring-session-redis-demo) 共享session,将session存在redis中，

形成session 跨域共享，或是说成单点登录的一种实现形式

通过浏览器访问一个web页面时，服务器端会自动创建一个session，默认情况下session是保存在服务器内存中的。

该项目集成了spring-session,spring-session会拦截所有的请求，对request和response进行包装，重写HttpServletRequest 的getSession方法，这样服务器创建的session

其实是spring包装后的session。

参考：[Spring Session 内部实现原理（源码分析）](https://www.jianshu.com/p/1001e9e2cfcf)
	  [Spring-Session实现Session共享实现原理以及源码解析](https://www.cnblogs.com/aflyun/p/8532230.html)
spring-session 具体是如何创建session的，可从参考[Spring Session产生的sessionid与cookies中的sessionid不一样的问题 && httpOnly 设置不起作用的问题??](https://www.cnblogs.com/imyjy/p/9187168.html) 入手
springboot 项目在用户成功登录后，后台同时发送一个请求至需要与之共享session的系统，传递sessionId ,这样当用户访问另一个系统时服务器就不会创建新的session，而是直接从
redis中获取原有session.

项目搭建过程中需要注意的几个问题：

1、该项目是基于springboot 1.x 版本，2.x版本，貌似有所不同
2、启动类上需要添加@EnableRedisHttpSession标签来启动redis session
3、共享的系统之间redis数据库的配置需保持一致
