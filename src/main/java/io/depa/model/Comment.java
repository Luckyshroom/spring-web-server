package io.depa.model;

import io.depa.model.audit.UserDateAudit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object that represents a Comment.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "comment")
public class Comment extends UserDateAudit implements Comparable<Comment> {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "postComment",
            joinColumns = @JoinColumn(name = "commentId"),
            inverseJoinColumns = @JoinColumn(name = "postId"))
    private Post post;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "commentVote",
            joinColumns = @JoinColumn(name = "commentId"),
            inverseJoinColumns = @JoinColumn(name = "voteId"))
    private List<Vote> votes = new ArrayList<>();

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public Post getPost() {
        return post;
    }
    public List<Vote> getVotes() {
        return votes;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public int compareTo(Comment comment) {
        return Integer.compare(this.id, comment.id);
    }
}