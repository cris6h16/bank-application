package com.devsu.hackerearth.backend.account.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.message.publish.OutboxEventPublisher;
import com.devsu.hackerearth.backend.account.model.entities.OutboxEvent;
import com.devsu.hackerearth.backend.account.model.enums.AggregateTypeEnum;
import com.devsu.hackerearth.backend.account.model.enums.EventTypeEnum;
import com.devsu.hackerearth.backend.account.model.enums.OutboxStatusEnum;
import com.devsu.hackerearth.backend.account.repository.OutboxRepository;

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
}