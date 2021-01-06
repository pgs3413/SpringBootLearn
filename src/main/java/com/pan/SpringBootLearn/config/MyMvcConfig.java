package com.pan.SpringBootLearn.config;
/*
保留SpringMVC原有的配置，增加了额外配置
目的是注册了一个配置好的WebMvcConfigurer进容器中
springboot会获得容器中所有的WebMvcConfigurer 并调用其中的方法
全面接管springMVC 加@enableWebMVc
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//第一种写法
//@Configuration
//public class MyMvcConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//    registry.addViewController("/success").setViewName("success");
//    }
//}

//第二种写法
@Configuration
public class MyMvcConfig{

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/success2").setViewName("success");
            }
        };
    }

}
