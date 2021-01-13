package com.pan.SpringBootLearn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "books")
public class Book {
    private Integer id;
    private Integer price;
    private String bookName;
    private String author;
}
