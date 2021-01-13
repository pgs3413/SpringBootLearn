package com.pan.SpringBootLearn.repository;

import com.pan.SpringBootLearn.pojo.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
    //方法名符合规范，会自动实现方法体
    public List<Book> findBooksByAuthor(String author);
}
