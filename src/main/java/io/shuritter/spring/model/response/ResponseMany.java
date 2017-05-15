package io.shuritter.spring.model.response;

import io.shuritter.spring.model.BaseEntity;

import java.util.List;

public class ResponseMany <T extends BaseEntity> extends Response {
    private Integer total;
    private Integer limit;
    private Integer skip;
    private List<T> data;

    public ResponseMany() {
    }

    public ResponseMany(List<T> data, Integer total, Integer limit, Integer skip) {
        this.total = total;
        this.limit = limit;
        this.skip = skip;
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ResponseMany<?> that = (ResponseMany<?>) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (limit != null ? !limit.equals(that.limit) : that.limit != null) return false;
        return skip != null ? skip.equals(that.skip) : that.skip == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (skip != null ? skip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResponseMany{" +
                "total=" + total +
                ", limit=" + limit +
                ", skip=" + skip +
                ", data=" + data +
                '}';
    }
}
