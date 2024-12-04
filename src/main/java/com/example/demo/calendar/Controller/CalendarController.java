package com.example.demo.calendar.Controller;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.DTO.ValidationGroup;
import com.example.demo.calendar.repository.CalendarRepository;
import com.example.demo.calendar.repository.UserRepository;
import com.example.demo.calendar.exception.IdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class CalendarController {
    CalendarRepository calendarDao;
    UserRepository userDao;

    public CalendarController(CalendarRepository calendarDao, UserRepository userDao) {
        this.calendarDao = calendarDao;
        this.userDao = userDao;
    }

    @GetMapping("/all/{date}/{id}")
    public ResponseEntity<List<Calendar>> getCalender(@PathVariable Long id, @PathVariable("date") String date) throws ClassNotFoundException, SQLException, IdException {
        return new ResponseEntity<>(calendarDao.getCalendar(id, date),HttpStatus.OK);
    }

    @GetMapping("/portion")
    public ResponseEntity<Calendar> getPortionCalender(@RequestBody User user) throws SQLException, ClassNotFoundException, IdException {
        return new ResponseEntity<>(calendarDao.getPortionCalendar(user),HttpStatus.OK);
    }

    @GetMapping("/page-nation")
    public ResponseEntity<List<Calendar>> getPageCalendar(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws SQLException, ClassNotFoundException, IdException {
        return new ResponseEntity<>(calendarDao.getPageCalendar(page, pageSize),HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Calendar> setCalendar(@Validated(ValidationGroup.Calendar.class) @RequestBody AllRounder allRounder) throws SQLException, ClassNotFoundException, IdException {
        Calendar calendar = calendarDao.addCalender(allRounder);
        return new ResponseEntity<>(calendar, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Calendar> changeDetails(@Validated(ValidationGroup.Login.class) @RequestBody AllRounder allRounder) throws SQLException, ClassNotFoundException, IdException {
        userDao.login(allRounder.getUser());
         return new ResponseEntity<>(calendarDao.changeDetails(allRounder),HttpStatus.OK);
    }

    @DeleteMapping("/{details}")
    public ResponseEntity<?> deleteCalendar(@RequestHeader("name") String name,@RequestHeader("password") String password, @PathVariable("details") String detail) throws SQLException, ClassNotFoundException, IdException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.login(user);
        calendarDao.deleteCalendar(detail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
