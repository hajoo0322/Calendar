package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.exception.IdException;
import com.example.demo.calendar.repository.dbconnecter.OldRepository;
import com.example.demo.calendar.repository.user_execution.LoginStatement;
import com.example.demo.calendar.repository.user_execution.NameChangeStatement;
import com.example.demo.calendar.repository.user_execution.UserStatement;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {
    UserCalendarFactory userCalendarFactory;

    public UserRepository(UserCalendarFactory userCalendarFactory) {
        this.userCalendarFactory = userCalendarFactory;
    }


    public void add(User user) throws ClassNotFoundException, SQLException, IdException {
        UserStatement<User> userUserStatement = userCalendarFactory.userAddStatement();
        userUserStatement.userStatement(user);
    }

    public User login(User user) throws ClassNotFoundException, SQLException, IdException {
        UserStatement<User> userUserStatement = userCalendarFactory.loginStatement();
        return userUserStatement.userStatement(user);

    }

    public User userNameChanger(AllRounder allRounder) throws ClassNotFoundException, SQLException, IdException {
        UserStatement<AllRounder> userStatement = userCalendarFactory.changeNameStatement();
        return userStatement.userStatement(allRounder);
    }
}
