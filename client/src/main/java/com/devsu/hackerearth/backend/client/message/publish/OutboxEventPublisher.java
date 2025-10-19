package com.devsu.hackerearth.backend.client.message.publish;

import com.devsu.hackerearth.backend.client.model.enums.AggregateTypeEnum;
import com.devsu.hackerearth.backend.client.model.enums.EventTypeEnum;

public interface OutboxEventPublisher {
    void publish(AggregateTypeEnum aggregateType, Long aggregateId, EventTypeEnum eventType);
    void markAsProcessed(Long outboxId);
}
