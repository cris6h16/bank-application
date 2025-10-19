package com.devsu.hackerearth.backend.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.message.consume.EventQueue;
import com.devsu.hackerearth.backend.account.model.dto.SyncEventDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventQueue eventQueue;

    @PostMapping
    public ResponseEntity<Void> pushEvent(@RequestBody SyncEventDto dto) {
        eventQueue.push(dto);
        return ResponseEntity.noContent().build();
    }
}
