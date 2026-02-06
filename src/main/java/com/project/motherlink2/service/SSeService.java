package com.project.motherlink2.service;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SSeService {

    // Thread-safe list of all connected clients
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // Called by frontend when they connect to SSE
    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Long-lived connection

        emitters.add(emitter);

        // Optional: send initial welcome or current state
        try {
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("Dashboard updates connected"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }

        // Clean up automatically
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(()     -> emitters.remove(emitter));
        emitter.onError((ex)     -> emitters.remove(emitter));

        return emitter;
    }

    // The method ALL your controllers will call
    public void broadcast(String eventName, Object payload) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(payload));   // can be String, Map, DTO, List, etc.
            } catch (IOException | IllegalStateException e) {
                deadEmitters.add(emitter); // connection closed
            }
        }

        emitters.removeAll(deadEmitters);
    }
}