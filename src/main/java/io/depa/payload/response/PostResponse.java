package io.depa.payload.response;

import io.depa.payload.UserSummary;

import java.time.Instant;
import java.util.List;

public class PostResponse {
    private int id;
    private String image;
    private String title;
    private String content;
    private String urlName;
    private Instant createdAt;
    private UserSummary createdBy;
    private List<TagResponse> tags;

    public int getId() {
        return id;
    }
    public String getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getUrlName() {
        return urlName;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public UserSummary getCreatedBy() {
        return createdBy;
    }
    public List<TagResponse> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }
    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }
}
