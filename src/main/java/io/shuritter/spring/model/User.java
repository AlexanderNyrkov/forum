package io.shuritter.spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "USER", schema = "public")
@ToString(callSuper = true, exclude = {"posts","comments"})
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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


    @JsonManagedReference(value = "1")
    @OneToMany(fetch = EAGER, mappedBy= "userId", cascade = ALL)
    @Setter @Getter
    private List<Post> posts;


    @JsonManagedReference(value = "2")
    @OneToMany(fetch = EAGER, mappedBy = "userId", cascade = ALL)
    @Setter @Getter
    private List<Comment> comments;

    public User() {
        super();
    }

    public User(String name, String login, String password, String email, List<Post> posts, List<Comment> comments) {
        super();
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.posts = posts;
        this.comments = comments;
    }
}
