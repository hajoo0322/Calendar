package com.example.demo.calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarDao {

    JdbcRepository jdbcRepository;

    public CalendarDao() {
        this.jdbcRepository = new OldRepository();
    }

    public void addCalender(UserCalendarRequest calendar) throws ClassNotFoundException, SQLException {
        Connection c = jdbcRepository.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into calendar(id, userName, date, details) values (?,?,?,?)"
        );
        ps.setLong(1, calendar.getUser().getId());
        ps.setString(2, calendar.getCalendar().getUserName());
        ps.setString(3, calendar.getCalendar().getDate());
        ps.setString(4, calendar.getCalendar().getDetails());
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public List<Calendar> getCalendar(User user, String date) throws ClassNotFoundException, SQLException {
        List<Calendar> calendarList = new ArrayList<>();  // 결과를 담을 리스트

        Connection c = jdbcRepository.makeConnection();
        String sqlQuery = ""; // 쿼리가 문자열로 작성되니까 객체에 담아서 파라미터로 전달하도록함

        if (user.getId() != null && date != null) {// 둘모두 값을 가지고있을때
            sqlQuery = "SELECT  * FROM calendar WHERE date = ? AND id = ? ";
        } else if (date == null) { //날짜에 해당하는 값이 없을때
            sqlQuery = "SELECT  * FROM calendar WHERE id = ?";
        } else if (user.getId() == null) { // 아이디에 해당하는 값이 없을때
            sqlQuery = "SELECT  * FROM calendar WHERE date = ?";
        } else if (date == null && user.getId() == null) {//둘다 없으면 찾을수 없으니까 예외던지기
            throw new SQLException();
        }

        PreparedStatement ps = c.prepareStatement(sqlQuery);
        int index = 1;
        if (date != null) {//날짜가 있다면 1번에 들어가고 증가시키기
            ps.setString(index++, date);
        }
        if (user.getId() != null) {//만약날짜가 없었다면 그대로1 날짜가있다면2가되서 정상작동
            ps.setLong(index, user.getId());
        }

        ResultSet rs = ps.executeQuery();

        // ResultSet으로 데이터 처리하여 List에 담기
        while (rs.next()) {
            Calendar calendar = new Calendar();
            calendar.setUserName(rs.getString("userName"));
            calendar.setDate(rs.getString("date"));
            calendar.setDetails(rs.getString("details"));
            calendarList.add(calendar);  // 리스트에 추가
        }

        rs.close();
        ps.close();
        c.close();

        return calendarList;  // 전체 리스트 반환
    }

    public Calendar getPortionCalendar(User user) throws ClassNotFoundException, SQLException {
        Connection c = jdbcRepository.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT  * FROM calendar WHERE id = ? LIMIT 1"
        );                    //1개만 조회하도록 변경
        ps.setLong(1, user.getId());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Calendar calendar = new Calendar();
            calendar.setUserName(rs.getString("userName"));
            calendar.setDate(rs.getString("date"));
            calendar.setDetails(rs.getString("details"));

            rs.close();
            ps.close();
            c.close();
            return calendar;
        } else {
            rs.close();
            ps.close();
            c.close();
            throw new SQLException();
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

    public void deleteCalendar(Calendar calendar) throws SQLException, ClassNotFoundException {
        Connection c = jdbcRepository.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "DELETE FROM calendar WHERE details = ?"
        );
        ps.setString(1, calendar.getDetails());
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
