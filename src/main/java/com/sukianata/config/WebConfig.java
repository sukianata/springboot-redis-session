package com.sukianata.config;

import com.sukianata.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @ Author     ：huangfan
 * @ Date       ：14:44 2018/12/24
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        /**
         * 添加登录拦截器,对指定路径的请求进行拦截
         * springboot 的contextPath 默认为 /
         * addPathPatterns
         * excludePathPatterns是指定不经过拦截器的路径，若访问的路径在项目中不存在会自动把请求变成/error
         **/
          registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/resources/**", "/login/**", "/error");
    }
    
    /**
     * @author huangfan
     * @description 启用配置类后需要在此处配置视图解析路径，application.properties中的配置会失效
     * @date 2018/12/25 9:55 
     * @return org.springframework.web.servlet.view.InternalResourceViewResolver
     **/
    
    @Bean
    public InternalResourceViewResolver setupViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
