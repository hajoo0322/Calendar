package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.exception.IdException;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import com.example.demo.calendar.repository.user_execution.AddStatement;
import com.example.demo.calendar.repository.user_execution.LoginStatement;
import com.example.demo.calendar.repository.user_execution.NameChangeStatement;
import com.example.demo.calendar.repository.user_execution.UserStatement;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {
    JdbcRepository jdbcRepository;

    public UserRepository(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    public void add(User user) throws ClassNotFoundException, SQLException, IdException {
        UserStatement<User> userUserStatement = new AddStatement(jdbcRepository);
        userUserStatement.userStatement(user);
    }

    public User login(User user) throws ClassNotFoundException, SQLException, IdException {
        UserStatement<User> userUserStatement = new LoginStatement(jdbcRepository);
        return userUserStatement.userStatement(user);

    }

    public User userNameChanger(AllRounder allRounder) throws ClassNotFoundException, SQLException, IdException {
        UserStatement<AllRounder> userStatement = new NameChangeStatement(jdbcRepository);
        return userStatement.userStatement(allRounder);
    }
}
