package com.Kaushik.Exchange.service;

import com.Kaushik.Exchange.dto.MessageFromOrderbook;
import com.Kaushik.Exchange.dto.MessageToEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.UUID;
import redis.clients.jedis.Jedis;

@Component
public class RedisManager {
    private final Jedis redisClient;
    private final ObjectMapper objectMapper;

    public RedisManager() {
        this.redisClient = new Jedis();
        this.objectMapper = new ObjectMapper();
        System.out.println("RedisManager initialized.");
    }

    public MessageFromOrderbook sendAndAwait(MessageToEngine message) {
        try {
            String clientId = UUID.randomUUID().toString();
            String messageJson = objectMapper.writeValueAsString(message);

            System.out.println("Sending message to Redis: " + messageJson);

            redisClient.lpush("messages", messageJson);
            String response = redisClient.blpop(0, clientId).get(1);

            System.out.println("Received response from Redis: " + response);

            return objectMapper.readValue(response, MessageFromOrderbook.class);
        } catch (Exception e) {
            System.err.println("Error communicating with Redis: " + e.getMessage());
            throw new RuntimeException("Error communicating with Redis", e);
        }
    }
}