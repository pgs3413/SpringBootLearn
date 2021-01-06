package com.pan.SpringBootLearn.controller;

import com.pan.SpringBootLearn.pojo.Dog;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
