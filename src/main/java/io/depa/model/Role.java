package io.depa.model;

import javax.persistence.*;

/**
 * Simple JavaBean object that represents Role of {@link User}.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Name name;

    public Role() {
    }

    public Role(Name name) {
        this.name = name;
    }

    public enum Name {ROLE_ADMIN, ROLE_AUTHOR, ROLE_EDITOR, ROLE_OWNER, ROLE_USER}

    public int getId() {
        return id;
    }
    public Name getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(Name name) {
        this.name = name;
    }
}
