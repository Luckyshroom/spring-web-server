package io.depa.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VoteRequest {
    @NotNull
    private int commentId;

    @NotBlank
    @Size(min = -1, max = 1)
    private int value;

    public int getCommentId() {
        return commentId;
    }
    public int getValue() {
        return value;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
