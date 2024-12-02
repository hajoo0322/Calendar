package com.example.demo.calendar.Controller;

import com.example.demo.calendar.entity.Calendar;
import com.example.demo.calendar.entity.User;
import com.example.demo.calendar.entity.UserCalendarRequest;
import com.example.demo.calendar.service.CalendarDao;
import com.example.demo.calendar.service.UserDao;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class CalendarController {

    @GetMapping("/getAll/{date}")
    public List<Calendar> getCalender(@RequestBody User user, @PathVariable("date") String date) throws ClassNotFoundException, SQLException {
        CalendarDao calendarDao = new CalendarDao(); // 컨테이너는 어떻게해야할까...
        return calendarDao.getCalendar(user, date);
    }

    @GetMapping("/getPortion")
    public Calendar getPortionCalender(@RequestBody User user) throws SQLException, ClassNotFoundException {
        CalendarDao calendarDao = new CalendarDao();
        return calendarDao.getPortionCalendar(user);
    }

    @GetMapping("/pageGet")
    public List<Calendar> getPageCalendar(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws SQLException, ClassNotFoundException {
        CalendarDao calendarDao =new CalendarDao();
        return calendarDao.getPageCalendar(page, pageSize);

    }

    @PostMapping("/addCalendar")
    public void setCalendar(@RequestBody UserCalendarRequest calendar) throws SQLException, ClassNotFoundException {
        CalendarDao calendarDao = new CalendarDao();
        calendarDao.addCalender(calendar);
        // 캘린더가 성공적으로 등록되엇다는걸 뭘로 반환해줄까...
    }

    @PutMapping("/changeDetails/{detail}")
    public Calendar changeDetails(@RequestBody UserCalendarRequest userCalendar, @PathVariable("detail") String detail) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.login(userCalendar.getUser());
        CalendarDao calendarDao = new CalendarDao();
        return calendarDao.changeDetails(userCalendar.getCalendar(), detail);
    }

    @DeleteMapping("/delete")
    public void deleteCalendar(@RequestBody UserCalendarRequest userCalendar) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.login(userCalendar.getUser());
        CalendarDao calendarDao = new CalendarDao();
        calendarDao.deleteCalendar(userCalendar.getCalendar());
    }
}
