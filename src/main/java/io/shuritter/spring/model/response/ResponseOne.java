package io.shuritter.spring.model.response;

import io.shuritter.spring.model.BaseEntity;

public class ResponseOne <T extends BaseEntity> extends Response {

    private T data;

    public ResponseOne() {
    }

    public ResponseOne(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseOne<?> that = (ResponseOne<?>) o;

        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ResponseOne{" +
                "data=" + data +
                '}';
    }
}
