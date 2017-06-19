package io.shuritter.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Comment entity class
 * Extends {@link BaseEntity}
 * @author Alexander Nyrkov
 */
@Entity
@Table(name = "COMMENT", schema = "PUBLIC")
@ToString(callSuper = true, exclude = {"userId","postId"})
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {

    @JsonBackReference(value = "2")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @Getter @Setter
    private User userId;

    @Column(name = "LIKE_COUNT")
    @Getter @Setter
    private Long like;

    @Column(name = "TEXT")
    @Getter @Setter
    private String text;


    @JsonBackReference(value = "3")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POST_ID")
    @Getter @Setter
    private Post postId;

    /**
     * Default constructor
     * @see BaseEntity
     */
    public Comment() {
        super();
        this.like = 0L;
    }

    public Comment(String text) {
        super();
        this.text = text;
    }

    public Comment(User userId, String text, Post postId) {
        super();
        this.userId = userId;
        this.like = 0L;
        this.text = text;
        this.postId = postId;
    }
}
