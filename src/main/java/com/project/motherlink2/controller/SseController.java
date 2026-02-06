package com.project.motherlink2.controller;

import com.project.motherlink2.service.SSeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {

    @Autowired
    private SSeService sseService;

    @GetMapping(value = "/api/dashboard-updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamDashboardUpdates() {
        return sseService.createEmitter();
    }
}