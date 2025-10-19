package com.devsu.hackerearth.backend.client.error.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrors {
    CLIENT_NOT_FOUND("Client not found", "CLIE001"),
    CLIENT_DNI_ALREADY_EXISTS("Client dni already exists", "CLIE002");

    private String message;
    private String code;
}
