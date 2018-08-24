package io.depa.model;

import io.depa.model.audit.UserDateAudit;

import javax.persistence.*;
import java.util.List;

/**
 * Simple JavaBean domain object that represents a Vote.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "vote")
public class Vote extends UserDateAudit implements Comparable<Vote> {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int value;

    @ManyToMany(cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "commentVote",
            joinColumns = @JoinColumn(name = "voteId"),
            inverseJoinColumns = @JoinColumn(name = "commentId"))
    private List<Comment> comments;

    public Vote() {
    }

    public Vote(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }
    public int getVote() {
        return value;
    }
    public List<Comment> getComments() {
        return comments;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setVote(int value) {
        this.value = value;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int compareTo(Vote vote) {
        return Integer.compare(this.id, vote.id);
    }
}
