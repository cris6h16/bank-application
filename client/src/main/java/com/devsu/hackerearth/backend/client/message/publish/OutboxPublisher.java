package com.devsu.hackerearth.backend.client.message.publish;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.repository.OutboxRepository;
import com.devsu.hackerearth.backend.client.model.entities.OutboxEvent;
import com.devsu.hackerearth.backend.client.model.enums.OutboxStatusEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OutboxPublisher {
    private final OutboxRepository outboxRepository;
    private final EventPublisher eventPublisher;

    @Scheduled(fixedDelayString = "5000") // should load from properties
    public void publishPendingEvents() {
        List<OutboxEvent> events = outboxRepository.findByStatusOrderByCreatedAtAsc(OutboxStatusEnum.PENDING);
        
        for (OutboxEvent event : events) {
            try {
                eventPublisher.publish(event);
                event.setStatus(OutboxStatusEnum.SENT);

            } catch (Exception e) {
                event.setStatus(OutboxStatusEnum.FAILED);
                log.error("Failed to publish event {}: {}", event.getId(), e.getMessage());
            }
        }

        outboxRepository.saveAll(events);
    }
}
