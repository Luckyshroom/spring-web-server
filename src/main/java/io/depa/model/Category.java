package io.depa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean object that represents Category of {@link Post}.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Name name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "categoryPost",
            joinColumns = @JoinColumn(name = "categoryId"),
            inverseJoinColumns = @JoinColumn(name = "postId"))
    private List<Post> posts = new ArrayList<>();

    public Category() {
    }

    public Category(Name name) {
        this.name = name;
    }

    public enum Name {ARTICLE, BLOG, NEWS}

    public int getId() {
        return id;
    }
    public Name getName() {
        return name;
    }
    public List<Post> getPosts() {
        return posts;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(Name name) {
        this.name = name;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
