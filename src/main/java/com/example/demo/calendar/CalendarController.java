package com.example.demo.calendar;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class CalendarController {

    @GetMapping("/getAll/{date}")
    public List<Calendar> getCalender(@RequestBody User user,@PathVariable("date") String date) throws ClassNotFoundException, SQLException {
        CalendarDao calendarDao = new CalendarDao(); // 컨테이너는 어떻게해야할까...
        return calendarDao.getCalendar(user, date);
    }
    @GetMapping("/getPortion")
    public Calendar getPortionCalender(@RequestBody User user) throws SQLException, ClassNotFoundException {
        CalendarDao calendarDao = new CalendarDao();
        return calendarDao.getPortionCalendar(user);
    }

    @PostMapping
    public void setCalender(@RequestBody Calendar calendar,@RequestBody User user) throws SQLException, ClassNotFoundException {
        CalendarDao calendarDao = new CalendarDao();
        calendarDao.addCalender(calendar,user);
        // 캘린더가 성공적으로 등록되엇다는걸 뭘로 반환해줄까...
    }
}
