package io.depa.payload;

import java.util.List;

public class UserProfile {
    private int id;
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String avatar;
    private String country;
    private String gender;
    private List<String> roles;

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getAvatar() {
        return avatar;
    }
    public String getCountry() {
        return country;
    }
    public String getGender() {
        return gender;
    }
    public List<String> getRoles() {
        return roles;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
