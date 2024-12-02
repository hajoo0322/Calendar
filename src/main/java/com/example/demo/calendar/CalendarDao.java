package com.example.demo.calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalendarDao {

    public void addCalender(Calendar calendar, User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/newboard", "root", "123123"
        );
        PreparedStatement ps = c.prepareStatement(
                "insert into calendar(id, userName, date, details) values (?,?,?,?)"
        );
        ps.setLong(1, user.getId());
        ps.setString(2, calendar.getUserName());
        ps.setString(3, calendar.getDate());
        ps.setString(4, calendar.getDetails());
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public List<Calendar> getCalendar(User user, String date) throws ClassNotFoundException, SQLException {
        List<Calendar> calendarList = new ArrayList<>();  // 결과를 담을 리스트

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/newboard", "root", "123123"
        );
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
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/newboard", "root", "123123"
        );

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
            return calendar;
        } else {
            throw new SQLException();
        }

        public
    }
}
