package com.devsu.hackerearth.backend.client.message.publish;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.client.model.dto.SyncEventDto;
import com.devsu.hackerearth.backend.client.model.entities.OutboxEvent;
import com.devsu.hackerearth.backend.client.model.enums.AggregateTypeEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncEventPublisher implements EventPublisher { // here i should use kfafk template, but to make this simple
                                                            // impl are http calls instead of publish in kafka topics

    private final RestTemplate restTemplate;

    @Override
    public void publish(OutboxEvent event) {
        final SyncEventDto dto = SyncEventDto.builder()
                .outboxId(event.getId())
                .aggregateId(event.getAggregateId())
                .eventType(event.getEventType())
                .build();

        if (Objects.equals(event.getAggregateType(), AggregateTypeEnum.CLIENT)) { // requests to account (or any service subscribed to CLIENT topics) service (:8000)
            ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8000/api/events", dto,
                    Void.class);
            log.info("SYNC PUBLISHER STATUS {} FOR EVENT {}", response.getStatusCode(), dto);
        }
    }

}
