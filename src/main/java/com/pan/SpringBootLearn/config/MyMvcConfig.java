package com.pan.SpringBootLearn.config;
/*
保留SpringMVC原有的配置，增加了额外配置
目的是注册了一个配置好的WebMvcConfigurer进容器中
springboot会获得容器中所有的WebMvcConfigurer 并调用其中的方法
全面接管springMVC 加@enableWebMVc
*/

import com.pan.SpringBootLearn.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
            //视图控制器
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/success").setViewName("success");
                registry.addViewController("/put").setViewName("put");
                registry.addViewController("/pan").setViewName("pan");
                registry.addViewController("/file").setViewName("file");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/hello");
            }
        };
    }

}
