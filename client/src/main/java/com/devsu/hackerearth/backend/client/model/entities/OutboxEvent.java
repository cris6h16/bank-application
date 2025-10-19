package com.devsu.hackerearth.backend.client.model.entities;

import com.devsu.hackerearth.backend.client.model.enums.*;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
@Entity
public class OutboxEvent extends Base {

    @Column(name = "aggregate_id", nullable = false)
    private Long aggregateId;

    @Enumerated(EnumType.STRING)
    private AggregateTypeEnum aggregateType;

    @Enumerated(EnumType.STRING)
    private EventTypeEnum eventType;

    @Enumerated(EnumType.STRING)
    private OutboxStatusEnum status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onPersist() {
        this.setCreatedAt(Instant.now());
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.setUpdatedAt(Instant.now());
    }
}