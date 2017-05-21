package io.shuritter.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "POST", schema = "public")
@ToString(callSuper = true, exclude = {"userId","comments"})
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity implements Serializable{

    @Column(name = "LIKE_COUNT")
    @Getter @Setter
    private Long like;

    @Column(name = "TEXT")
    @Getter @Setter
    private String text;

    @JsonBackReference(value = "1")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @Getter @Setter
    private User userId;

    @JsonManagedReference(value = "3")
    @OneToMany(fetch = EAGER, mappedBy = "postId")
    @Setter @Getter
    private List<Comment> comments;

    public Post() throws ParseException {
        super();
        this.like = 0L;
    }

    public Post(String text, User userId, List<Comment> comments) {
        super();
        this.like = 0L;
        this.text = text;
        this.userId = userId;
        this.comments = comments;
    }
}
