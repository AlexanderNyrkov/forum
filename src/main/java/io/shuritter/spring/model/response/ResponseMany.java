package io.shuritter.spring.model.response;

import io.shuritter.spring.model.BaseEntity;
import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = true)
public class ResponseMany <T extends BaseEntity> extends Response {

    @Setter @Getter
    private Integer total;

    @Setter @Getter
    private Integer limit;

    @Setter @Getter
    private Integer skip;

    @Setter @Getter
    private List<T> data;

    public ResponseMany() {
        super();
    }

    public ResponseMany(Integer total, Integer limit, Integer skip, List<T> data) {
        super();
        this.total = total;
        this.limit = limit;
        this.skip = skip;
        this.data = data;
    }
}
