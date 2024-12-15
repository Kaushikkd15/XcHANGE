//package com.Kaushik.Exchange.controller;
//
//import com.Kaushik.Exchange.dto.MessageFromOrderbook;
//import com.Kaushik.Exchange.dto.MessageToEngine;
//import com.Kaushik.Exchange.service.RedisManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/v1/depth")
//public class DepthController {
//
//    private final RedisManager redisManager;
//
//    @Autowired
//    public DepthController(RedisManager redisManager) {
//        this.redisManager = redisManager;
//        System.out.println("DepthController initialized.");
//    }
//
//    @GetMapping
//    public MessageFromOrderbook getDepth(@RequestParam String symbol) {
//        System.out.println("Received depth request: symbol=" + symbol);
//
//        MessageToEngine message = new MessageToEngine("",new Object());
//        message.setType("GET_DEPTH");
//        message.setData(Map.of("market", symbol));
//
//        MessageFromOrderbook response = redisManager.sendAndAwait(message);
//
//        System.out.println("Depth response: " + response);
//        return response;
//    }
//}


package com.Kaushik.Exchange.service;
//
//import com.Kaushik.Exchange.dto.MessageFromOrderbook;
//import com.Kaushik.Exchange.dto.MessageToEngine;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import redis.clients.jedis.Jedis;
//
//import static java.lang.Math.*;
//
//@Component
//public class RedisManager {
//    private final Jedis redisClient;
//    private final ObjectMapper objectMapper;
//
//    public RedisManager() {
//        this.redisClient = new Jedis();
//        this.objectMapper = new ObjectMapper();
//        System.out.println("RedisManager initialized.");
//    }
//    public String getRandomClientId() {
//        return Double.toString(Math.random()).substring(2, 15) +
//                Double.toString(Math.random()).substring(2, 15);
//    }
//    public MessageFromOrderbook sendAndAwait(MessageToEngine message) {
//        try {
//            String clientId = getRandomClientId();
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> payload = new HashMap<>();
//            payload.put("clientId", clientId);
//            payload.put("message", message);
//            String jsonString = objectMapper.writeValueAsString(payload);
//
//            System.out.println("Sending message to Redis: " + jsonString);
//
//            redisClient.lpush(clientId, jsonString);
//            String response = redisClient.brpop(0, clientId).get(1);
//            System.out.println("Received response from Redis: " + response);
//
//            return objectMapper.readValue(response, MessageFromOrderbook.class);
//        } catch (Exception e) {
//            System.err.println("Error communicating with Redis: " + e.getMessage());
//            throw new RuntimeException("Error communicating with Redis", e);
//        }
//    }
//}
