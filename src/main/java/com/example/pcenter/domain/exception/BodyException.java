package com.example.pcenter.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class BodyException {

    private String message;
    private Map<String, String> errors;

    public BodyException(final String message) {
        this.message = message;
    }
}
