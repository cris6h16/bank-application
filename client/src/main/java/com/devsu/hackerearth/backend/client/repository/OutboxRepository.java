package com.devsu.hackerearth.backend.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.client.model.entities.OutboxEvent;
import com.devsu.hackerearth.backend.client.model.enums.OutboxStatusEnum;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findByStatusOrderByCreatedAtAsc(OutboxStatusEnum pending);
}
