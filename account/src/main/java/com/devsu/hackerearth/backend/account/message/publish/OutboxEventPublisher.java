package com.devsu.hackerearth.backend.account.message.publish;

import com.devsu.hackerearth.backend.account.model.enums.AggregateTypeEnum;
import com.devsu.hackerearth.backend.account.model.enums.EventTypeEnum;

public interface OutboxEventPublisher {
    void publish(AggregateTypeEnum aggregateType, Long aggregateId, EventTypeEnum eventType);
}
