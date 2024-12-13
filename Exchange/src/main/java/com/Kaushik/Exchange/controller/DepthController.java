package com.Kaushik.Exchange.controller;

import com.Kaushik.Exchange.dto.MessageFromOrderbook;
import com.Kaushik.Exchange.dto.MessageToEngine;
import com.Kaushik.Exchange.service.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/depth")
public class DepthController {

    private final RedisManager redisManager;

    @Autowired
    public DepthController(RedisManager redisManager) {
        this.redisManager = redisManager;
        System.out.println("DepthController initialized.");
    }

    @GetMapping
    public MessageFromOrderbook getDepth(@RequestParam String symbol) {
        System.out.println("Received depth request: symbol=" + symbol);

        MessageToEngine message = new MessageToEngine();
        message.setType("GET_DEPTH");
        message.setData(Map.of("market", symbol));

        MessageFromOrderbook response = redisManager.sendAndAwait(message);

        System.out.println("Depth response: " + response);
        return response;
    }
}

