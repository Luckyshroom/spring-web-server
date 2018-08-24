package io.depa.model;

import com.ibm.icu.text.Transliterator;
import io.depa.model.audit.UserDateAudit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object that represents a Post.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "post")
public class Post extends UserDateAudit implements Comparable<Post> {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, unique = true)
    private String title;

    @Lob
    @Column(nullable = false, length = 1000000)
    private String content;

    @Column(nullable = false, unique = true)
    private String urlName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "contextPost",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "contextId"))
    private Context context;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "categoryPost",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "postComment",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "commentId"))
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "postTag",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "tagId"))
    private List<Tag> tags = new ArrayList<>();

    public Post() {}

    public Post(String image, String title, String content) {
        this.title = title;
        this.image = image;
        this.content = content;
        this.urlName = Transliterator.getInstance("Russian-Latin/BGN; Any-Latin; NFKD; Any-Lower").transliterate(title)
                .replaceAll("[^a-zA-Z- ]", "")
                .replaceAll("[\\s]+", "-");
    }

    public int getId() {
        return id;
    }
    public String getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getUrlName() {
        return urlName;
    }
    public Context getContext() {
        return context;
    }
    public Category getCategory() {
        return category;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public List<Tag> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public int compareTo(Post post) {
        return Integer.compare(this.id, post.id);
    }
}
