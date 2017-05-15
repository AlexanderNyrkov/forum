package io.shuritter.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Entity
@Table(name = "POST", schema = "public")
public class Post extends BaseEntity implements Serializable{

    @Column(name = "CREATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    private static final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "LIKE_COUNT")
    private Long like = 0L;

    @Column(name = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted = false;

    public Post() {
    }

    public Post(LocalDateTime updatedAt, Long like, String text, User user, List<Comment> comments, Boolean isDeleted) {
        this.updatedAt = updatedAt;
        this.like = like;
        this.text = text;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime update) {
        this.updatedAt = update;
    }

    public LocalDateTime getCreate() {
        return createdAt;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

        Post post = (Post) o;

        if (createdAt != null ? !createdAt.equals(post.createdAt) : post.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(post.updatedAt) : post.updatedAt != null) return false;
        if (like != null ? !like.equals(post.like) : post.like != null) return false;
        if (text != null ? !text.equals(post.text) : post.text != null) return false;
        if (user != null ? !user.equals(post.user) : post.user != null) return false;
        if (comments != null ? !comments.equals(post.comments) : post.comments != null) return false;
        return isDeleted != null ? isDeleted.equals(post.isDeleted) : post.isDeleted == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (like != null ? like.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", update=" + updatedAt +
                ", like=" + like +
                ", text='" + text + '\'' +
                ", deleted=" + isDeleted +
                '}';
    }
}
