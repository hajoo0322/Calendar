package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.exception.IdException;

import java.sql.SQLException;

public interface CalendarStatement<E,F> {

    F calendarStatement(E E) throws SQLException, ClassNotFoundException, IdException;
}
