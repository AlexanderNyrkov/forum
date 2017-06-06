package io.shuritter.spring.model.response;

import io.shuritter.spring.model.BaseEntity;
import lombok.*;

/**
 * Class for a single response
 * @param <T> the type of entities
 * Extends {@link Response}
 * @author Alexander Nyrkov
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class ResponseOne <T extends BaseEntity> extends Response {

    @Setter @Getter
    private T data;

    /**
     * Default constructor
     * @see Response
     */
    public ResponseOne() {
        super();
    }

    public ResponseOne(T data) {
        super();
        this.data = data;
    }
}
