package com.example.demo.calendar.Controller;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.repository.CalendarRepository;
import com.example.demo.calendar.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getAll/{date}/{id}")
    public List<Calendar> getCalender(@PathVariable Long id, @PathVariable("date") String date) throws ClassNotFoundException, SQLException {
        return calendarDao.getCalendar(id, date);
    }

    @GetMapping("/getPortion")
    public Calendar getPortionCalender(@RequestBody User user) throws SQLException, ClassNotFoundException {
        return calendarDao.getPortionCalendar(user);
    }

    @GetMapping("/pageGet")
    public List<Calendar> getPageCalendar(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws SQLException, ClassNotFoundException {
        return calendarDao.getPageCalendar(page, pageSize);

    }

    @PostMapping("/addCalendar")
    public ResponseEntity<Calendar> setCalendar(@RequestBody AllRounder allRounder) throws SQLException, ClassNotFoundException {
        Calendar calendar = calendarDao.addCalender(allRounder);
        return new ResponseEntity<>(calendar, HttpStatus.OK);
    }

    @PatchMapping("/changeDetails")
    public ResponseEntity<Calendar> changeDetails(@RequestBody AllRounder allRounder) throws SQLException, ClassNotFoundException {
        userDao.login(allRounder.getUser());
         return new ResponseEntity<>(calendarDao.changeDetails(allRounder),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{details}")
    public void deleteCalendar(@RequestHeader("name") String name,@RequestHeader("password") String password, @PathVariable("details") String detail) throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.login(user);
        calendarDao.deleteCalendar(detail);
    }
}
