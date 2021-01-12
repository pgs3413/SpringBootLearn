package com.pan.SpringBootLearn.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class People implements Serializable {
    private Integer id;
    private String name;
}
