package com.devsu.hackerearth.backend.client.message.publish;

import com.devsu.hackerearth.backend.client.model.entities.OutboxEvent;

public interface EventPublisher {
    public void publish(OutboxEvent event);
}
