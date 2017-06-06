package io.shuritter.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

/**
 * Post entity class
 * Extends {@link BaseEntity}
 * @author Alexander Nyrkov
 */
@Entity
@Table(name = "POST", schema = "public")
@ToString(callSuper = true, exclude = {"userId","comments"})
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {

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

    /**
     * Default constructor
     * @see BaseEntity
     */
    public Post() {
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
