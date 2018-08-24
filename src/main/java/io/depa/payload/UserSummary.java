package io.depa.payload;

import io.depa.model.Role;

import java.util.List;

public class UserSummary {
    private int id;
    private String avatar;
    private String firstname;
    private String lastname;
    private String username;
    private List<Role> roles;

    public UserSummary(int id,
                       String avatar,
                       String firstname,
                       String lastname,
                       String username,
                       List<Role> roles) {
        this.id = id;
        this.avatar = avatar;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }
    public String getAvatar() {
        return avatar;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getUsername() {
        return username;
    }
    public List<Role> getRoles() {
        return roles;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
