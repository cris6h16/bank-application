package com.devsu.hackerearth.backend.account.message.consume;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.error.application.ApplicationException;
import com.devsu.hackerearth.backend.account.model.dto.SyncEventDto;
import com.devsu.hackerearth.backend.account.model.enums.AggregateTypeEnum;
import com.devsu.hackerearth.backend.account.model.enums.EventTypeEnum;
import com.devsu.hackerearth.backend.account.service.AccountService;
import com.devsu.hackerearth.backend.account.service.OutboxService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {
    private final EventQueue eventQueue;
    private final AccountService accountService;
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
            case CLIENT_DELETED:
                handleClientDeleted(event.getAggregateId());
                break;
            default:
                break;

        }
    }

    private void handleClientDeleted(Long id) {
        try {
            accountService.deleteByClientId(id);
            log.info("Client with id={} deleted successfully", id);

        } catch (ApplicationException e) {
            log.info("Client with id={} not found, nothing to delete");
        }

        outboxService.publish(AggregateTypeEnum.CLIENT, id, EventTypeEnum.CLIENT_DELETED_PROCESSED);
    }
}
