package io.depa.model;

import io.depa.model.audit.DateAudit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object that represents a User.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "user")
public class User extends DateAudit implements Comparable<User> {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade =  CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Profile profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles = new ArrayList<>();

    public User() {}

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Profile getProfile() {
        return profile;
    }
    public List<Role> getRoles() {
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
    public void setPassword(String password) {
        this.password = password;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(this.id, user.id);
    }
}
