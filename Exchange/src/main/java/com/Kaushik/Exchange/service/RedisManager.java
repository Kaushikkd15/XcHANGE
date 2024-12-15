package com.Kaushik.Exchange.service;


import com.Kaushik.Exchange.dto.MessageFromOrderbook;
import com.Kaushik.Exchange.dto.MessageToEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisManager {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisSubscriber redisSubscriber;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ConcurrentHashMap<String, CompletableFuture<MessageFromOrderbook>> responseMap = new ConcurrentHashMap<>();

    public CompletableFuture<MessageFromOrderbook> sendAndAwait(MessageToEngine message) {
        try {
            // Generate unique clientId
            String clientId = UUID.randomUUID().toString();

            // Register a CompletableFuture for this clientId
            CompletableFuture<MessageFromOrderbook> future = new CompletableFuture<>();
            responseMap.put(clientId, future);

            // Subscribe to the channel for this clientId
            redisSubscriber.subscribe(clientId, (response) -> {
                System.out.println("Callback triggered for clientId: " + clientId + ", response: " + response);
                try {
                    // Parse the response and complete the future

                    MessageFromOrderbook messageFromOrderbook = objectMapper.readValue(response, MessageFromOrderbook.class);
                    future.complete(messageFromOrderbook);
                } catch (Exception e) {
                    future.completeExceptionally(e);
                } finally {
                    // Cleanup
                    redisSubscriber.unsubscribe(clientId);
                    responseMap.remove(clientId);
                }
            });

            // Publish the message to the 'messages' queue
            String payload = objectMapper.writeValueAsString(new MessagePayload(clientId, message));
            System.out.println(2);
            System.out.println(payload);
            redisTemplate.opsForList().leftPush("messages", payload);

            return future;

        } catch (Exception e) {
            throw new RuntimeException("Error in Redis communication", e);
        }
    }

    public void onMessage(String clientId, String message) {
        try {
            // Retrieve the CompletableFuture associated with the clientId
            CompletableFuture<MessageFromOrderbook> future = responseMap.get(clientId);

            if (future != null) {
                // Deserialize the message string into a MessageFromOrderbook object
                MessageFromOrderbook messageFromOrderbook = objectMapper.readValue(message, MessageFromOrderbook.class);

                // Complete the CompletableFuture with the deserialized object
                future.complete(messageFromOrderbook);
            } else {
                System.err.println("No future found for clientId: " + clientId);
            }
        } catch (Exception e) {
            System.err.println("Error deserializing message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Nested class to represent payload structure
    private static class MessagePayload {
        public String clientId;
        public MessageToEngine message;

        public MessagePayload(String clientId, MessageToEngine message) {
            this.clientId = clientId;
            this.message = message;
        }
    }
}
