package io.shuritter.spring.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Entity
@Table(name = "COMMENT")
public class Comment extends BaseEntity implements Serializable{

    @Column(name = "AUTHOR")
    @ManyToOne
    @JoinColumn(name = "ID", nullable = false)
    private User author;

    @Column(name = "LIKE_COUNT")
    private Long like;

    @Column(name = "CREATED_AT")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DATE)
    private DateTime create;

    @Column(name = "UPDATED_AT")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DATE)
    private DateTime update;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "POST")
    @ManyToOne
    @JoinColumn(name = "ID")
    private Post postId;

    @Column(name = "IS_DELETED")
    private Boolean deleted;

    public Comment() {
    }

    public Comment(User author, Long like, DateTime create, DateTime update, String text, Post postId, boolean deleted) {
        this.author = author;
        this.like = like;
        this.create = create;
        this.update = update;
        this.text = text;
        this.postId = postId;
        this.deleted = deleted;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long likesCount) {
        this.like = likesCount;
    }

    public DateTime getCreate() {
        return create;
    }

    public void setCreate(DateTime dateOfComment) {
        this.create = dateOfComment;
    }

    public DateTime getUpdate() {
        return update;
    }

    public void setUpdate(DateTime update) {
        this.update = update;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (deleted != comment.deleted) return false;
        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (author != null ? !author.equals(comment.author) : comment.author != null) return false;
        if (like != null ? !like.equals(comment.like) : comment.like != null) return false;
        if (create != null ? !create.equals(comment.create) : comment.create != null) return false;
        if (update != null ? !update.equals(comment.update) : comment.update != null) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        return postId != null ? postId.equals(comment.postId) : comment.postId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (like != null ? like.hashCode() : 0);
        result = 31 * result + (create != null ? create.hashCode() : 0);
        result = 31 * result + (update != null ? update.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "id=" + id + ", author=" + author +
                ", likes count=" + like +
                ", date of comment=" + create +
                ", date of update comment=" + update +
                ", text: " + text +
                "post id =" + postId;
    }
}
