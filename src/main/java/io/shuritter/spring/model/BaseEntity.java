package io.shuritter.spring.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

/**
 * Base class that describes domain entity
 * @author Alexander Nyrkov
 */
@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    @Getter
    @Setter
    protected String id;


    @Column(name = "CREATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    @Getter
    protected Date createdAt;


    @Column(name = "UPDATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    @Getter
    @Setter
    protected Date updatedAt;

    @Column(name = "IS_DELETED")
    @Getter
    @Setter
    protected Boolean isDeleted;

    /**
     * Default constructor
     */
    public BaseEntity() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isDeleted = false;
    }


    public BaseEntity(Boolean isDeleted) {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isDeleted = isDeleted;
    }
}
