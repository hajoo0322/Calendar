package com.example.demo.calendar.repository.controller_execution;

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


        String sqlQuery = "SELECT  * FROM calendar WHERE id = ?"; // 쿼리가 문자열로 작성되니까 객체에 담아서 파라미터로 전달하도록함

        try (Connection c = jdbcRepository.makeConnection();
             PreparedStatement ps = c.prepareStatement(sqlQuery)) {

            ps.setLong(1, allrounder.getId());

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
