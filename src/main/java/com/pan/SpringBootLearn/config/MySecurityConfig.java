package com.pan.SpringBootLearn.config;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//SpringSecurity配置类
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf校验
        http.csrf().disable();
        //定制请求的授权规则
        http.authorizeRequests()
                .antMatchers("/success").permitAll()
                .antMatchers("/pan").hasRole("pan");
        //开启自动配置的登录功能 无权限跳转到/login
        http.formLogin();
        //开启自动配置的注销功能 请求/logout 清空session
        http.logout();
        //开启记住我功能
        http.rememberMe();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //定制认证规则
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("pan").password(new BCryptPasswordEncoder().encode("12345")).roles("pan");
    }
}
