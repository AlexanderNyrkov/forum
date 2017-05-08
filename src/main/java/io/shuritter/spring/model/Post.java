package io.shuritter.spring.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Entity
@Table(name = "POST")
public class Post extends BaseEntity implements Serializable{

    @Column(name = "CREATED_AT")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DATE)
    private DateTime create;

    @Column(name = "UPDATED_AT")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DATE)
    private DateTime update;

    @Column(name = "LIKE_COUNT")
    private Long like;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "USER_ID")
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postId")
    private List<Comment> comments;

    @Column(name = "IS_DELETED")
    private Boolean deleted;

    public Post() {
    }

    public Post(DateTime create, DateTime update, String link, Long like, String text, User user, List<Comment> comments, boolean deleted) {
        this.create = create;
        this.update = update;
        this.like = like;
        this.text = text;
        this.user = user;
        this.comments = comments;
        this.deleted = deleted;
    }

    public DateTime getCreate() {
        return create;
    }

    public void setCreate(DateTime dateOfPublication) {
        this.create = dateOfPublication;
    }

    public DateTime getUpdate() {
        return update;
    }

    public void setUpdate(DateTime update) {
        this.update = update;
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

    public void setText(String comment) {
        this.text = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

        Post post = (Post) o;

        if (deleted != post.deleted) return false;
        if (id != null ? !id.equals(post.id) : post.id != null) return false;
        if (create != null ? !create.equals(post.create) : post.create != null) return false;
        if (update != null ? !update.equals(post.update) : post.update != null) return false;
        if (like != null ? !like.equals(post.like) : post.like != null) return false;
        if (text != null ? !text.equals(post.text) : post.text != null) return false;
        if (user != null ? !user.equals(post.user) : post.user != null) return false;
        return comments != null ? comments.equals(post.comments) : post.comments == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (create != null ? create.hashCode() : 0);
        result = 31 * result + (update != null ? update.hashCode() : 0);
        result = 31 * result + (like != null ? like.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", create=" + create +
                ", update=" + update +
                ", like=" + like +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", comments=" + comments +
                ", deleted=" + deleted +
                '}';
    }
}
