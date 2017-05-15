package io.shuritter.spring.model.response;

public enum  Status {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
