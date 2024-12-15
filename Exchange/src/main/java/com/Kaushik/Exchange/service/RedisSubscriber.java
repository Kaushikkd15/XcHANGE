package com.Kaushik.Exchange.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class RedisSubscriber implements MessageListener {

    @Autowired
    private RedisMessageListenerContainer redisContainer;

    private final ConcurrentHashMap<String, Consumer<String>> listeners = new ConcurrentHashMap<>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());

        // Notify the appropriate listener
//        Consumer<String> listener = listeners.get(channel);
//        if (listener != null) {
//            listener.accept(body);
//        }
    }

    public void subscribe(String channel, Consumer<String> listener) {
        System.out.println("Subscribing to Redis channel: " + channel);
        redisContainer.addMessageListener((message, pattern) -> {
            String body = new String(message.getBody());
            listener.accept(body);
        }, new ChannelTopic(channel));
    }

    public void unsubscribe(String channel) {
        redisContainer.removeMessageListener((message, pattern) -> {}, new ChannelTopic(channel));
        System.out.println("Unsubscribed from Redis channel: " + channel);
    }
}
