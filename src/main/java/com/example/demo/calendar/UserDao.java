package com.example.demo.calendar;

import java.sql.*;


public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/newboard", "root", "123123"
        );

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
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/newboard", "root", "123123"
        );
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

    public User userNameChanger(UserRequest user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/newboard", "root", "123123"
        );
        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE name = ?"
        );
        ps.setString(1,user.getUser().getName());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("password").equals(user.getUser().getPassword())) {
                PreparedStatement ps2 = c.prepareStatement(
                        "UPDATE users SET name = ? WHERE name = ?"
                );
                ps2.setString(1, user.getChangeUserName().getName());
                ps2.setString(2, user.getUser().getName());
                ps2.executeUpdate();
                user.getUser().setName(user.getChangeUserName().getName());
                return user.getUser();
            } else {
                throw new SQLException("비밀번호가 잘못됨");
            }
        } else {
            throw new SQLException("기존의 저장된 유저의 정보가 없음");
        }
    }
}
