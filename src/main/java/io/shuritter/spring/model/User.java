package io.shuritter.spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * User entity class
 * Extends {@link BaseEntity}
 * @author Alexander Nyrkov
 */
@Entity
@Table(name = "USER", schema = "public")
@ToString(callSuper = true, exclude = {"posts","comments"})
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "NAME")
    @Getter @Setter
    private  String name;


    @Column(name = "LOGIN")
    @Getter @Setter
    private String login;


    @Column(name = "PASSWORD")
    @Getter @Setter
    private String password;


    @Column(name = "EMAIL")
    @Getter @Setter
    private String email;


    @Column(name = "IS_ADMIN")
    @Getter @Setter
    private Boolean isAdmin;


    @JsonManagedReference(value = "1")
    @OneToMany(fetch = EAGER, mappedBy= "userId", cascade = ALL)
    @Setter @Getter
    private List<Post> posts;


    @JsonManagedReference(value = "2")
    @OneToMany(fetch = EAGER, mappedBy = "userId", cascade = ALL)
    @Setter @Getter
    private List<Comment> comments;

    /**
     * Default constructor
     * @see BaseEntity
     */
    public User() {
        super();
        this.isAdmin = false;
    }

    public User(String name, String login, String password, String email, Boolean isAdmin, List<Post> posts, List<Comment> comments) {
        super();
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.posts = posts;
        this.comments = comments;
    }
}
