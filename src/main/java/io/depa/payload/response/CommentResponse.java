package io.depa.payload.response;

import io.depa.payload.UserSummary;

import java.time.Instant;

public class CommentResponse {
    private int id;
    private String content;
    private Instant createdAt;
    private UserSummary createdBy;

    public int getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }
}
