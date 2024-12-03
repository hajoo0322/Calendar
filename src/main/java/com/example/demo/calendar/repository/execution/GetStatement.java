package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetStatement implements CalendarStatement<AllRounder, List<Calendar>> {
    JdbcRepository jdbcRepository;

    public GetStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public List<Calendar> calendarStatement(AllRounder allrounder) throws SQLException, ClassNotFoundException {
        List<Calendar> calendarList = new ArrayList<>();  // 결과를 담을 리스트


        String sqlQuery = ""; // 쿼리가 문자열로 작성되니까 객체에 담아서 파라미터로 전달하도록함

        if (allrounder.getId() != null && allrounder.getDate() != null) {// 둘모두 값을 가지고있을때
            sqlQuery = "SELECT  * FROM calendar WHERE date = ? AND id = ? ";
        } else if (allrounder.getDate() == null) { //날짜에 해당하는 값이 없을때
            sqlQuery = "SELECT  * FROM calendar WHERE id = ?";
        } else if (allrounder.getId() == null) { // 아이디에 해당하는 값이 없을때
            sqlQuery = "SELECT  * FROM calendar WHERE date = ?";
        } else if (allrounder.getDate() == null && allrounder.getId() == null) {//둘다 없으면 찾을수 없으니까 예외던지기
            throw new SQLException();
        }
        try (Connection c = jdbcRepository.makeConnection();
             PreparedStatement ps = c.prepareStatement(sqlQuery)) {
            int index = 1;
            if (allrounder.getDate() != null) {//날짜가 있다면 1번에 들어가고 증가시키기
                ps.setString(index++, allrounder.getDate());
            }
            if (allrounder.getId() != null) {//만약날짜가 없었다면 그대로1 날짜가있다면2가되서 정상작동
                ps.setLong(index, allrounder.getId());
            }

            try (ResultSet rs = ps.executeQuery()) {

                // ResultSet으로 데이터 처리하여 List에 담기
                while (rs.next()) {
                    Calendar calendar = new Calendar();
                    calendar.setUserName(rs.getString("userName"));
                    calendar.setDate(rs.getString("date"));
                    calendar.setDetails(rs.getString("details"));
                    calendarList.add(calendar);  // 리스트에 추가
                }

                return calendarList;
            }
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 문제발생" + e.getMessage());
        }
    }
}
