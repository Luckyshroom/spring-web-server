package io.depa.payload.request;

import javax.validation.constraints.*;
import java.util.List;

public class PostRequest {
    @NotBlank
    @Size(max = 140)
    private String title;

    @NotBlank
    @Size(max = 140)
    private String image;

    @NotBlank
    @Size(min = 140, max = 1000000)
    private String content;

    @NotBlank
    @Size(max = 40)
    private String context;

    @Size(max = 4)
    private List<String> tags;

    public String getTitle() {
        return title;
    }
    public String getImage() {
        return image;
    }
    public String getContent() {
        return content;
    }
    public String getContext() {
        return context;
    }
    public List<String> getTags() {
        return tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
