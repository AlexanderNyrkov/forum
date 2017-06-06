package io.shuritter.spring.model.response;

import lombok.*;

import java.io.Serializable;

/**
 * Abstract base class that describes response data
 * @author Alexander Nyrkov
 */
@ToString
@EqualsAndHashCode
public abstract class Response implements Serializable {

    @Setter @Getter
    private String apiVersion;

    @Setter @Getter
    private Status status;

    /**
     * Default constructor
     */
    public Response() {
        this.apiVersion = "1.0";
        this.status = Status.SUCCESS;
    }
}
