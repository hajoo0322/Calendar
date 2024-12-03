package com.example.demo.calendar.Controller;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.repository.CalendarRepository;
import com.example.demo.calendar.repository.UserRepository;
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
    public void setCalendar(@RequestBody AllRounder allRounder) throws SQLException, ClassNotFoundException {
        calendarDao.addCalender(allRounder);
        // 캘린더가 성공적으로 등록되엇다는걸 뭘로 반환해줄까...
    }

    @PatchMapping("/changeDetails/{detail}")
    public Calendar changeDetails(@RequestBody AllRounder allRounder, @PathVariable("detail") String detail) throws SQLException, ClassNotFoundException {
        userDao.login(allRounder.getUser());
         return calendarDao.changeDetails(allRounder.getCalendar(), detail);
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
