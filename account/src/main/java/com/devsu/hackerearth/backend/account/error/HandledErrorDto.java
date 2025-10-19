package com.devsu.hackerearth.backend.account.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@ToString
public class HandledErrorDto {
    private String timestamp;
    private String message;
    private String code;
    private HttpStatus status;
    private String path;
}
