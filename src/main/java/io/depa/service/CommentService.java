package io.depa.service;

import io.depa.exception.ResourceNotFoundException;
import io.depa.model.Comment;
import io.depa.model.Post;
import io.depa.model.User;
import io.depa.repository.CommentRepository;
import io.depa.repository.PostRepository;
import io.depa.repository.UserRepository;
import io.depa.payload.request.CommentRequest;
import io.depa.payload.response.CommentResponse;
import io.depa.payload.response.PagedResponse;
import io.depa.util.Helpers;
import io.depa.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Service class for {@link Comment}.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment createByPost(int postId, CommentRequest commentRequest) {
        Comment comment = new Comment(commentRequest.getContent());
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // Set Post
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    public Comment getById(int id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    public PagedResponse<CommentResponse> getByPost(int postId, int page, int size) {
        // Retrieve Comments
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Page<Comment> comments = commentRepository.findByPost(post, pageable);

        if (comments.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), comments.getNumber(),
                    comments.getSize(), comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
        }
        // Map Comments to CommentResponses containing post creator details
        Map<Integer, User> creatorMap = Helpers.getCreatorMap(comments.getContent(), userRepository);

        List<CommentResponse> commentResponses = comments.map(comment -> ModelMapper
                .toCommentResponse(comment, creatorMap.get(comment.getCreatedBy()))).getContent();

        return new PagedResponse<>(commentResponses, comments.getNumber(),
                comments.getSize(), comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
