package com.pan.SpringBootLearn.test;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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
}
