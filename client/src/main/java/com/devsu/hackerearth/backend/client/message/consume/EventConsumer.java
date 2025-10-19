package com.devsu.hackerearth.backend.client.message.consume;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.client.error.application.ApplicationException;
import com.devsu.hackerearth.backend.client.model.dto.SyncEventDto;
import com.devsu.hackerearth.backend.client.service.OutboxService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final EventQueue eventQueue;
    private final OutboxService outboxService;

    @Scheduled(fixedDelay = 5000)
    public void processEvents() {
        while (!eventQueue.isEmpty()) {
            SyncEventDto event = eventQueue.poll();
            log.info("processing event {}", event);
            handleEvent(event);
        }
    }

    private void handleEvent(SyncEventDto event) {
        switch (event.getEventType()) {
            case CLIENT_DELETED_PROCESSED:
                handleClientDeletedProcessed(event.getOutboxId());
                break;
            default:
                break;

        }
    }

    private void handleClientDeletedProcessed(Long outboxId) {
        try {
            outboxService.markAsProcessed(outboxId);
            log.info("Outbox with id={} marked as processed", outboxId);

        } catch (ApplicationException e) {
            log.info("Outbox with id={} failed when trying to mark as processed, message={}, code={}", outboxId, e.getError().getMessage(),
                    e.getError().getCode());
        }
    }
}
