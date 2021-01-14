package com.pan.SpringBootLearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
public class FileController {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("str") String str,
                         @RequestPart("single") MultipartFile single,
                         @RequestPart("multi") MultipartFile[] multi) throws IOException {
        System.out.println(str);
        System.out.println(single.getOriginalFilename());
        System.out.println(multi.length);
        if(!single.isEmpty()){
            String s1=single.getOriginalFilename();
            single.transferTo(new File("D:\\"+s1));
        }
        if(multi.length>0){
            for(MultipartFile file:multi){
                String s2=file.getOriginalFilename();
                file.transferTo(new File("D:\\"+s2));
            }
        }
     return "success";
    }
}
