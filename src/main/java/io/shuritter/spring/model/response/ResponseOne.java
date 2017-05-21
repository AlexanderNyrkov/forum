package io.shuritter.spring.model.response;

import io.shuritter.spring.model.BaseEntity;
import lombok.*;

@ToString
@EqualsAndHashCode(callSuper = true)
public class ResponseOne <T extends BaseEntity> extends Response {

    @Setter @Getter
    private T data;

    public ResponseOne() {
        super();
    }

    public ResponseOne(T data) {
        super();
        this.data = data;
    }
}
