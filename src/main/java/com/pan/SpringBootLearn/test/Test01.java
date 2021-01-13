package com.pan.SpringBootLearn.test;


import com.pan.SpringBootLearn.pojo.Book;
import com.pan.SpringBootLearn.pojo.Dog;
import com.pan.SpringBootLearn.pojo.People;
import com.pan.SpringBootLearn.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

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
    @Test
    public void test06(){
        People people=new People(4,"pan");
        rabbitTemplate.convertAndSend("exchage.direct","pan.people",people);
    }

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void test07(){
//        amqpAdmin.declareExchange(new DirectExchange("java.direct"));
//        amqpAdmin.declareQueue(new Queue("java.queue"));
        amqpAdmin.declareBinding(new Binding("java.queue",Binding.DestinationType.QUEUE,"java.direct","java",null));
        System.out.println("finish");

    }

    @Autowired
    BookRepository bookRepository;
    @Test
    public void test08(){
        Book book=new Book(6,60,"c++","pan");
        bookRepository.save(book);
    }
    @Test
    public void test09(){
//        Optional<Book> optional =bookRepository.findById(6);
//        Book book=optional.get();
//        System.out.println(book);
        List<Book> books=bookRepository.findBooksByAuthor("pan");
        System.out.println(books);
    }

    @Autowired
    JavaMailSenderImpl mailSender;
    @Test
    public void test10(){
        //普通消息
       SimpleMailMessage message=new SimpleMailMessage();
       message.setSubject("通知");
       message.setText("今晚开会");
       message.setFrom("2113114379@qq.com");
       message.setTo("2113114379@qq.com");
       mailSender.send(message);
    }
    @Test
    public void test11() throws MessagingException {
        //复杂消息
        MimeMessage mimeMessage =mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("通知");
        helper.setText("<h1>今晚开会</h1>",true);
        helper.setFrom("2113114379@qq.com");
        helper.setTo("2113114379@qq.com");
        helper.addAttachment("1.txt",new File("E:\\IdeaProjects\\SpringBootLearn\\src\\main\\java\\com\\pan\\SpringBootLearn\\test\\1.txt"));
        mailSender.send(mimeMessage);
    }
}
