package com.application.dtos;

public class RegisterRequest {

    public String firstname;
    public String lastname;
    public String username;
    public String email;

    public RegisterRequest() { }

    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

}
