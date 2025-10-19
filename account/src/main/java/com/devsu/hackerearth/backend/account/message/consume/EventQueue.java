package com.devsu.hackerearth.backend.account.message.consume;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.dto.SyncEventDto;

@Component
public class EventQueue {
    
    private final Queue<SyncEventDto> queue = new ConcurrentLinkedQueue<>();

    public void push(SyncEventDto event) {
        queue.add(event);
    }

    public SyncEventDto poll() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
