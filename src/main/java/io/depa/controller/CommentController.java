package io.depa.controller;

import io.depa.model.Comment;
import io.depa.payload.request.CommentRequest;
import io.depa.payload.response.ApiResponse;
import io.depa.payload.response.CommentResponse;
import io.depa.payload.response.PagedResponse;
import io.depa.service.CommentService;
import io.depa.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comment", params = "postId")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createByPost(@RequestParam(value = "postId") int postId,
                                                 @Valid @RequestBody CommentRequest commentRequest) {
        // Creating comment
        Comment comment = commentService.createByPost(postId, commentRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{commentId}")
                .buildAndExpand(comment.getId()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "Comment created successfully"));
    }

    @GetMapping(value = "/comments")
    public PagedResponse<CommentResponse> getByPost(
            @RequestParam(value = "postId") int postId,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return commentService.getByPost(postId, page, size);
    }
}
