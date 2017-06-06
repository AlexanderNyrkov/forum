package io.shuritter.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

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
    @Getter @Setter
    protected String id;


    @Column(name = "CREATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    @JsonSerialize(using=DateTimeSerializer.class)
    @Getter
    protected DateTime createdAt;


    @Column(name = "UPDATED_AT")
    @DateTimeFormat(iso = DATE_TIME)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    @JsonSerialize(using=DateTimeSerializer.class)
    @Getter @Setter
    protected DateTime updatedAt;

    @Column(name = "IS_DELETED")
    @Getter @Setter
    protected Boolean isDeleted;

    /**
     * Default constructor
     */
    public BaseEntity() {
        this.createdAt = DateTime.now();
        this.updatedAt = DateTime.now();
        this.isDeleted = false;
    }


    public BaseEntity(Boolean isDeleted) {
        this.createdAt = DateTime.now();
        this.updatedAt = DateTime.now();
        this.isDeleted = isDeleted;
    }
}
