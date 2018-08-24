package io.depa.model;

import javax.persistence.*;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String avatar;
    private String country;
    private String gender;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private User user;

    public Profile() {}

    private Profile(String firstname, String lastname, String avatar, String country, String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = avatar;
        this.gender = country;
        this.gender = gender;
    }

    public static class Builder {
        private String firstname;
        private String lastname;
        private String avatar;
        private String country;
        private String gender;

        public Builder(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public Builder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Profile build() {
            return new Profile(firstname, lastname, avatar, country, gender);
        }
    }

    public int getId() {
        return id;
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
    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
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
    public void setUser(User user) {
        this.user = user;
    }
}
