package com.devsu.hackerearth.backend.client.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.client.message.publish.OutboxEventPublisher;
import com.devsu.hackerearth.backend.client.model.entities.OutboxEvent;
import com.devsu.hackerearth.backend.client.model.enums.AggregateTypeEnum;
import com.devsu.hackerearth.backend.client.model.enums.EventTypeEnum;
import com.devsu.hackerearth.backend.client.model.enums.OutboxStatusEnum;
import com.devsu.hackerearth.backend.client.repository.OutboxRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class OutboxService implements OutboxEventPublisher {

    private final OutboxRepository outboxRepository;

    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    @Override
    public void publish(AggregateTypeEnum aggregateType, Long aggregateId, EventTypeEnum eventType) {
        OutboxEvent event = OutboxEvent.builder()
                .aggregateType(aggregateType)
                .aggregateId(aggregateId)
                .eventType(eventType)
                .status(OutboxStatusEnum.PENDING)
                .build();
        outboxRepository.save(event);

        log.info("Saved PENDING event for entityId={}, eventType={}", aggregateId, eventType);
    }

    @Override
    public void markAsProcessed(Long outboxId) {
        OutboxEvent event = outboxRepository.findById(outboxId)
                .orElseThrow(() -> new IllegalStateException(String.format("Outbox with id=%s should exists", outboxId)));
        event.setStatus(OutboxStatusEnum.PROCESSED);
        outboxRepository.save(event);

        log.info("Saved PROCESSED event for outboxId={}, entityId={}, eventType={}", outboxId, event.getAggregateId(), event.getEventType());

    }
}