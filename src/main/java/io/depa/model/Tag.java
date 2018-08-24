package io.depa.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean object that represents Tag for {@link Post}.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @Size(max = 40)
    private String name;

    @ManyToMany(cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "postTag",
            joinColumns = @JoinColumn(name = "tagId"),
            inverseJoinColumns = @JoinColumn(name = "postId"))
    private List<Post> posts = new ArrayList<>();

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
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
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
