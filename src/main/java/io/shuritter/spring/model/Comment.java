package io.shuritter.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Entity
@Table(name = "COMMENT", schema = "PUBLIC")
public class Comment extends BaseEntity implements Serializable{

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "LIKE_COUNT")
    private Long like = 0L;

    @Column(name = "CREATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    private static final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted = false;

    public Comment() {

    }

    public Comment(User user, Long like, LocalDateTime updatedAt, String text, Post post, Boolean isDeleted) {
        this.like = like;
        this.updatedAt = updatedAt;
        this.text = text;
        this.post = post;
        this.isDeleted = isDeleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public LocalDateTime getCreate() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime update) {
        this.updatedAt = update;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;
        if (like != null ? !like.equals(comment.like) : comment.like != null) return false;
        if (!createdAt.equals(createdAt)) return false;
        if (updatedAt != null ? !updatedAt.equals(comment.updatedAt) : comment.updatedAt != null) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (post != null ? !post.equals(comment.post) : comment.post != null) return false;
        return isDeleted != null ? isDeleted.equals(comment.isDeleted) : comment.isDeleted == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (like != null ? like.hashCode() : 0);
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", like=" + like +
                ", createdAt=" + createdAt +
                ", update=" + updatedAt +
                ", text='" + text + '\'' +
                ", deleted=" + isDeleted +
                '}';
    }
}
