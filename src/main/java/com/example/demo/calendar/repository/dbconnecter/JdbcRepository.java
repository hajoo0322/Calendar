package com.example.demo.calendar.repository.dbconnecter;

import java.sql.Connection;
import java.sql.SQLException;


public interface JdbcRepository {

    Connection makeConnection() throws ClassNotFoundException, SQLException;
}
