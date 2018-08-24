package io.depa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean object that represents Context for {@link Post}.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "context")
public class Context {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tagId", nullable = false, unique = true)
    private Tag tag;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "contextPost",
            joinColumns = @JoinColumn(name = "contextId"),
            inverseJoinColumns = @JoinColumn(name = "postId"))
    private List<Post> posts = new ArrayList<>();

    public Context() {}

    public Context(String name) {
        this.name = name;
        this.tag = new Tag(name);
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Tag getTag() {
        return tag;
    }
    public List<Post> getPosts() {
        return posts;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
