package com.devsu.hackerearth.backend.client.error.application;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private ApplicationErrors error;

    public ApplicationException(ApplicationErrors error, Object... params) {
        super(String.format(error.getMessage(), params));
        this.error = error;
    }
}
