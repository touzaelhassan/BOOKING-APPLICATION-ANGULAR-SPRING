package com.application.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private boolean isActive;
    private boolean isNotLocked;
    private String role;
    private String[] authorities;

    public User() { }

    public User(String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, String[] authorities) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.role = role;
        this.authorities = authorities;
    }

    public User(Integer id, String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role) {
        this.id = id;
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.role = role;
    }

    public void setId(Integer id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    public void setLastLoginDate(Date lastLoginDate) { this.lastLoginDate = lastLoginDate; }
    public void setLastLoginDateDisplay(Date lastLoginDateDisplay) { this.lastLoginDateDisplay = lastLoginDateDisplay; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
    public void setActive(boolean active) { isActive = active; }
    public void setNotLocked(boolean notLocked) { isNotLocked = notLocked; }
    public void setRole(String role) { this.role = role; }
    public void setAuthorities(String[] authorities) { this.authorities = authorities; }

    public Integer getId() { return id; }
    public String getUserId() { return userId; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public Date getLastLoginDate() { return lastLoginDate; }
    public Date getLastLoginDateDisplay() { return lastLoginDateDisplay; }
    public Date getJoinDate() { return joinDate; }
    public boolean isActive() { return isActive; }
    public boolean isNotLocked() { return isNotLocked; }
    public String getRole() { return role; }
    public String[] getAuthorities() { return authorities; }

}
