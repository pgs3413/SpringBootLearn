package com.pan.SpringBootLearn.controller;

import com.pan.SpringBootLearn.exception.UserNotExistException;
import com.pan.SpringBootLearn.mapper.PeopleMapper;
import com.pan.SpringBootLearn.pojo.Dog;
import com.pan.SpringBootLearn.pojo.People;
import com.pan.SpringBootLearn.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String test01(){
        return "hello world";
    }

    @GetMapping("/test02")
    @ResponseBody
    public String test02(@Valid Dog dog, BindingResult bindingResult){
        int count=bindingResult.getErrorCount();
        if(count>0){
            List<FieldError> errorList =bindingResult.getFieldErrors();
            FieldError error=errorList.get(0);
            String s=error.getDefaultMessage();
            return s;
        }else{
            return "name is "+dog.getName();
        }
    }

    @PutMapping("/test03")
    @ResponseBody
    public String test03(int id){
        return "对id为"+id+"进行修改";
    }

    @RequestMapping("/test04")
    @ResponseBody
    public String test04(String user){
        if(user.equals("aaa")){
           throw new UserNotExistException();
        }
        return "success";
    }

    @Autowired
    PeopleMapper peopleMapper;

    @RequestMapping("/test05")
    @ResponseBody
    public People test05(People people){
//        peopleMapper.insertPeople(people);
        return people;
    }
    @RequestMapping("/test06/{id}")
    @ResponseBody
    public People test06(@PathVariable("id") Integer id){
        return peopleMapper.getPeopleById(id);
    }

    @Autowired
    PeopleService peopleService;

    @RequestMapping("/test07/{id}")
    @ResponseBody
    public People test07(@PathVariable("id") Integer id){
        return peopleService.getPeople(id);
    }
}
