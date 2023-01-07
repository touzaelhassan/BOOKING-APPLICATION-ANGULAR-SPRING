package com.application.dtos;

public class LoginRequest {

    public String username;
    public String password;

    public LoginRequest() { }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }
    public String getUsername() { return username; }

}
