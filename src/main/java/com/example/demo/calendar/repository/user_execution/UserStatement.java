package com.example.demo.calendar.repository.user_execution;

import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.exception.IdException;

import java.sql.SQLException;

public interface UserStatement<E> {
    public User userStatement(E e) throws SQLException, ClassNotFoundException, IdException;
}
