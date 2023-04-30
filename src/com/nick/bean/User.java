package com.nick.bean;

public class User {
    private Integer userId;
    private String userName;
    private String userPwd;
    private String email;
    public User() {
    }
    public User(Integer userId, String userName, String userPwd, String email) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.email = email;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
