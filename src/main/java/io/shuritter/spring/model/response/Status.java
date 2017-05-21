package io.shuritter.spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  Status {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    @Getter
    private final String label;
}
