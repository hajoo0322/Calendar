package com.example.demo.calendar.Controller;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.exception.IdException;
import com.example.demo.calendar.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) throws SQLException, ClassNotFoundException, IdException {
        return new ResponseEntity<>(userDao.login(user), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) throws SQLException, ClassNotFoundException, IdException {
    userDao.add(user);
    return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PatchMapping("/change-username")
    public ResponseEntity<User> changeUserName(@RequestBody AllRounder allRounder) throws SQLException, ClassNotFoundException, IdException {
        userDao.login(allRounder.getUser());
        return new ResponseEntity<>(userDao.userNameChanger(allRounder),HttpStatus.OK);
    }
}
