package com.example.demo.calendar.repository.execution;

import java.sql.SQLException;

public interface CalendarStatement<E,F> {

    F calendarStatement(E E) throws SQLException, ClassNotFoundException;
}
