package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.DTO.UserRequest;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.*;


public class UserRepository {
    JdbcRepository jdbcRepository;

    public UserRepository(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = jdbcRepository.makeConnection();

        //서치를 해야함
        //예외처리 같은게있으면
        PreparedStatement ps = c.prepareStatement(
                "insert into users( name, password) values (?,?)"
        ); //id는 데이터 베이스에서 자동증가

        ps.setString(1, user.getName());
        ps.setString(2, user.getPassword());
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public User login(User user) throws ClassNotFoundException, SQLException {
        //로그인 서비스
        Connection c = jdbcRepository.makeConnection();
        String name = user.getName();
        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE name = ?"
        );
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("password").equals(user.getPassword())) {
                user.setId(rs.getLong("id"));
                return user;
            } else {
                throw new SQLException("비밀번호가 잘못됨");
            }
        } else {
            throw new SQLException("기존의 저장된 유저의 정보가 없음");
        }
    }

    public User userNameChanger(AllRounder allRounder) throws ClassNotFoundException, SQLException {
        Connection c = jdbcRepository.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE name = ?"
        );
        ps.setString(1,allRounder.getUser().getName());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("password").equals(allRounder.getUser().getPassword())) {
                PreparedStatement ps2 = c.prepareStatement(
                        "UPDATE users SET name = ? WHERE name = ?"
                );
                ps2.setString(1, allRounder.getUserNameChange().getName());
                ps2.setString(2, allRounder.getUser().getName());
                ps2.executeUpdate();
                allRounder.getUser().setName(allRounder.getUserNameChange().getName());
                return allRounder.getUser();
            } else {
                throw new SQLException("비밀번호가 잘못됨");
            }
        } else {
            throw new SQLException("기존의 저장된 유저의 정보가 없음");
        }
    }
}
