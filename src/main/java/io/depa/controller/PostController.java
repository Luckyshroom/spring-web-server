package io.depa.controller;

import io.depa.repository.PostRepository;
import io.depa.model.Post;
import io.depa.payload.request.PostRequest;
import io.depa.payload.response.ApiResponse;
import io.depa.payload.response.PagedResponse;
import io.depa.payload.response.PostResponse;
import io.depa.service.PostService;
import io.depa.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Post controller.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @PostMapping(value = "/post", params = "categoryId")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createByCategory(@RequestParam(value = "categoryId") int categoryId,
                                        @Valid @RequestBody PostRequest postRequest) {
        if (postRepository.existsByTitle(postRequest.getTitle())) {
            return new ResponseEntity<>(new ApiResponse(
                    false, "Post with the same title is already exist!"), HttpStatus.BAD_REQUEST);
        }
        // Creating post
        Post post = postService.createByCategory(categoryId, postRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postId}")
                .buildAndExpand(post.getId()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "Post created successfully"));
    }

    @GetMapping(value = "/post", params = "urlName")
    public PostResponse getByIndex(@RequestParam(value = "urlName") String urlName) {
        return postService.getByUrlName(urlName);
    }

    @GetMapping(value = "/posts", params = "categoryId")
    public PagedResponse<PostResponse> getByCategory(
            @RequestParam(value = "categoryId") int categoryId,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return postService.getByCategory(categoryId, page, size);
    }

    @GetMapping(value = "/posts", params = "tagId")
    public PagedResponse<PostResponse> getByTag(
            @RequestParam(value = "tagId") int tagId,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return postService.getByTag(tagId, page, size);
    }
}
