package io.shuritter.spring.model.response;

import lombok.*;

@ToString
@EqualsAndHashCode
public abstract class Response  {

    @Setter @Getter
    private String apiVersion;

    @Setter @Getter
    private Status status;

    public Response() {
        this.apiVersion = "1.0";
        this.status = Status.SUCCESS;
    }
}
