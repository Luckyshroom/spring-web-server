package io.depa.util;

import io.depa.model.Comment;
import io.depa.model.Post;
import io.depa.model.User;
import io.depa.payload.UserSummary;
import io.depa.payload.response.CommentResponse;
import io.depa.payload.response.PostResponse;
import io.depa.payload.response.TagResponse;

import java.util.List;
import java.util.stream.Collectors;

public interface ModelMapper {

    static CommentResponse toCommentResponse(Comment comment, User creator) {

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setId(comment.getId());
        commentResponse.setContent(comment.getContent());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setCreatedBy(toUserSummary(creator));

        return commentResponse;
    }

    static PostResponse toPostResponse(Post post, User creator) {

        PostResponse postResponse = new PostResponse();

        postResponse.setId(post.getId());
        postResponse.setImage(post.getImage());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setUrlName(post.getUrlName());
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setCreatedBy(toUserSummary(creator));

        List<TagResponse> tagResponses = post.getTags().stream().map(tag -> {
            TagResponse tagResponse = new TagResponse();
            tagResponse.setId(tag.getId());
            tagResponse.setName(tag.getName());
            return tagResponse;
        }).collect(Collectors.toList());
        postResponse.setTags(tagResponses);

        return postResponse;
    }

    static UserSummary toUserSummary(User user) {

        List<String> userRoles = user.getRoles().stream().map(role ->
                role.getName().toString()).collect(Collectors.toList());

        return new UserSummary(
                user.getId(),
                user.getProfile().getAvatar(),
                user.getProfile().getFirstname(),
                user.getProfile().getLastname(),
                user.getUsername(),
                user.getRoles());
    }
}
