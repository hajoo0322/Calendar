package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.DTO.UserCalendarRequest;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import com.example.demo.calendar.repository.execution.AddStatement;
import com.example.demo.calendar.repository.execution.CalendarStatement;
import com.example.demo.calendar.repository.execution.GetStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CalendarRepository {

    JdbcRepository jdbcRepository;

    public void addCalender(UserCalendarRequest calendar) throws ClassNotFoundException, SQLException {
        CalendarStatement<UserCalendarRequest,UserCalendarRequest> calendarStatement;
        calendarStatement = new AddStatement(jdbcRepository);
        calendarStatement.calendarStatement(calendar);
    }

    public List<Calendar> getCalendar(Long id, String date) throws ClassNotFoundException, SQLException {
        AllRounder allRounder = new AllRounder();
        allRounder.setId(id);
        allRounder.setDate(date);
        CalendarStatement<AllRounder,List<Calendar>> calendarStatement = new GetStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder);
    }

    public Calendar getPortionCalendar(User user) throws ClassNotFoundException, SQLException {
        try (Connection c = jdbcRepository.makeConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT  * FROM calendar WHERE id = ? LIMIT 1"
             )) {                    //1개만 조회하도록 변경
            ps.setLong(1, user.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Calendar calendar = new Calendar();
                    calendar.setUserName(rs.getString("userName"));
                    calendar.setDate(rs.getString("date"));
                    calendar.setDetails(rs.getString("details"));
                    return calendar;
                } else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                throw new SQLException("데이터베이스 연결실패");
            }
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("데이터베이스 드라이버를 찾을수 없습니다.");
        }
    }

    public Calendar changeDetails(Calendar calendar,String detail) throws ClassNotFoundException, SQLException {
        Connection c = jdbcRepository.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "UPDATE calendar SET details = ? WHERE details =?"
        );
        ps.setString(1,detail);
        ps.setString(2,calendar.getDetails());
        ps.executeUpdate();

        PreparedStatement ps2 = c.prepareStatement(
                "insert into calendar(date_modified) values (?)"
        );
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = now.format(dateTimeFormatter);
        ps2.setString(1,format);
        ps2.executeUpdate();

        calendar.setDetails(detail);

        ps2.close();
        ps.close();
        c.close();

        return calendar;
    }

    public void deleteCalendar(String detail) throws SQLException, ClassNotFoundException {
        Connection c = jdbcRepository.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "DELETE FROM calendar WHERE details = ?"
        );
        ps.setString(1, detail);
        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public List<Calendar> getPageCalendar(int page, int pageSize) throws ClassNotFoundException, SQLException {
        Connection c = jdbcRepository.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM calendar LIMIT ? OFFSET ?"
        );

        ps.setInt(1, pageSize);
        ps.setInt(2, (page-1)*pageSize);
        ResultSet rs = ps.executeQuery();
        List<Calendar> calendarList = new ArrayList<>();
        while (rs.next()) {
            Calendar calendar = new Calendar();
            calendar.setUserName(rs.getString("username"));
            calendar.setDetails(rs.getString("details"));
            calendar.setDate(rs.getString("date"));
            calendarList.add(calendar);
        }
        rs.close();
        ps.close();
        c.close();
        return calendarList;
    }
}
