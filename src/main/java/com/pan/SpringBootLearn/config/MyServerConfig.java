package com.pan.SpringBootLearn.config;

import com.pan.SpringBootLearn.servlet.MyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyServerConfig {
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean bean=new ServletRegistrationBean<>(new MyServlet(),"/servlet");
        return bean;
    }
}
