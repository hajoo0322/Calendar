package com.example.demo.calendar.repository.dbconnecter;

import java.sql.Connection;
import java.sql.SQLException;


public interface JdbcRepository {

    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}