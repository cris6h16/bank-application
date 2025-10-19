package com.devsu.hackerearth.backend.client.model.dto;

import com.devsu.hackerearth.backend.client.model.enums.EventTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SyncEventDto {
    private EventTypeEnum eventType;
    private Long aggregateId;
    private Long outboxId;
}
