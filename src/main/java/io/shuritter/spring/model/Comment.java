package io.shuritter.spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COMMENT", schema = "PUBLIC")
@ToString(callSuper = true, exclude = {"userId","postId"})
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity implements Serializable{

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

    public Comment() {
        super();
        this.like = 0L;
    }

    public Comment(User userId, String text, Post postId) {
        super();
        this.userId = userId;
        this.like = 0L;
        this.text = text;
        this.postId = postId;
    }
}
