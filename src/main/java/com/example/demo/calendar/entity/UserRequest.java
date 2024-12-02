package com.example.demo.calendar.entity;

public class UserRequest {
    private User user;
    private User changeUserName;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getChangeUserName() {
        return changeUserName;
    }

    public void setChangeUserName(User changeUserName) {
        this.changeUserName = changeUserName;
    }
}
