package com.pan.SpringBootLearn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog implements Serializable {

    @NotNull(message = "name cant be null")
    String name;

}
