package com.sukianata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author: huangfan
 * @date: 2018/12/24 15:03
 * @desc springboot 启动类
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableRedisHttpSession
public class SpringbootRedisSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRedisSessionApplication.class, args);
	}


}

