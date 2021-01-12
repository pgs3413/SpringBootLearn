package com.pan.SpringBootLearn.test;


import com.pan.SpringBootLearn.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class Test01 {
    @Autowired
    DataSource dataSource;

    @Test
    public void test01() throws SQLException {
        System.out.println(dataSource.getClass());//默认数据源 com.zaxxer.hikari.HikariDataSource
        Connection conn =dataSource.getConnection();
        System.out.println(conn);
        conn.close();
    }
    @Autowired
    StringRedisTemplate stringRedisTemplate; //kv都是字符串
    @Autowired
    RedisTemplate redisTemplate; //kv都是对象

    @Test
    public void test02(){
        ValueOperations<String,String> ops =stringRedisTemplate.opsForValue();
        ops.set("age","18");
        String s=ops.get("age");
        System.out.println(s);
    }
    @Test
    public void test03(){
        Dog dog=new Dog("mike");
        ValueOperations ops=redisTemplate.opsForValue();
        ops.set("dog",dog); //默认jdk序列化机制
        Dog dog1=(Dog)ops.get("dog");
        System.out.println(dog1);
    }

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void test04(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第二个消息");
        map.put("data", Arrays.asList("hello",123,true));
        rabbitTemplate.convertAndSend("exchage.direct","pan.news",map);
    }
    @Test
    public void test05(){
        //拿到消息队列中的第一个消息，反序列化为java对象，并删除它
        Object obj=rabbitTemplate.receiveAndConvert("pan.news");
        System.out.println(obj.getClass());
        System.out.println(obj);
    }
}
