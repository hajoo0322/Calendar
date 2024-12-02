package com.example.demo.calendar.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OldRepository implements JdbcRepository {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/newboard", "root", "123123"
        );                  //락은나중에~
        return c;
        //레포지토리에서 데이터베이스와 상호작용하는걸 다담당해야함
    }
}
