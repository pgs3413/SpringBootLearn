package com.pan.SpringBootLearn.service;

import com.pan.SpringBootLearn.mapper.PeopleMapper;
import com.pan.SpringBootLearn.pojo.People;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    @Autowired
    PeopleMapper peopleMapper;

    @Cacheable(cacheNames = "people")
    public People getPeople(Integer id){
        System.out.println("查询"+id+"号");
        return peopleMapper.getPeopleById(id);
    }

   // @RabbitListener(queues = "pan.people")
    public void messageListen(People people){
        System.out.println("收到一个消息："+people);
    }
}
