package io.depa.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @Email
    private String email;

    @Size(min = 4, max = 24)
    private String username;

    @Size(min = 4, max = 24)
    private String password;

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
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
}
