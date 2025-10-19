package com.devsu.hackerearth.backend.account.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutboxStatusEnum {
    PROCESSED,  
    FAILED, 
    PENDING, 
    SENT;
}
