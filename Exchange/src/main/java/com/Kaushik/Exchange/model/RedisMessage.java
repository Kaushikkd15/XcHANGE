package com.Kaushik.Exchange.model;

import com.Kaushik.Exchange.types.MessageType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RedisMessage {
    private MessageType type;
    private Map<String, Object> data;
    private Object payload;
    private String correlationId;
}