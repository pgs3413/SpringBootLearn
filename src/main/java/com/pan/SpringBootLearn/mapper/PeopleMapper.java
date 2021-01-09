package com.pan.SpringBootLearn.mapper;

import com.pan.SpringBootLearn.pojo.People;
import org.apache.ibatis.annotations.*;

//注解版
//@Mapper
//public interface PeopleMapper {
//    @Select("select * from people where id=#{id}")
//    public People getPeopleById(Integer id);
//    @Delete("delete from people where id = #{id}")
//    public int deletePeopleById(Integer id);
//    @Options(useGeneratedKeys = true,keyProperty = "id")
//    @Insert("insert into people(name) values(#{name})")
//    public int insertPeople(People people);
//    @Update("update people set name=#{name} where id=#{id}")
//    public int updatePeople(People people);
//}

//配置文件版
@Mapper
public interface PeopleMapper{
    public People getPeopleById(Integer id);
}