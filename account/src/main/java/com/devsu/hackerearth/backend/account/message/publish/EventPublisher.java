package com.devsu.hackerearth.backend.account.message.publish;

import com.devsu.hackerearth.backend.account.model.entities.OutboxEvent;

public interface EventPublisher {
    public void publish(OutboxEvent event);
}
