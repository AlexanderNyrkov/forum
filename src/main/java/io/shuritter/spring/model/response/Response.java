package io.shuritter.spring.model.response;

import io.shuritter.spring.model.BaseEntity;

public abstract class Response  {
    private String apiVersion = "1.0";
    private Status status;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (apiVersion != null ? !apiVersion.equals(response.apiVersion) : response.apiVersion != null) return false;
        return status == response.status;
    }

    @Override
    public int hashCode() {
        int result = apiVersion != null ? apiVersion.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "apiVersion='" + apiVersion + '\'' +
                ", status=" + status +
                '}';
    }
}
