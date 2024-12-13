package com.Kaushik.Exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class MessageFromOrderbook {
    private String type;
    private Object payload;

    // Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        System.out.println("Setting response type: " + type);
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        System.out.println("Setting response payload: " + payload);
        this.payload = payload;
    }
}
