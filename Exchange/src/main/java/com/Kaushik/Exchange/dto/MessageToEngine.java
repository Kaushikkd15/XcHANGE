package com.Kaushik.Exchange.dto;

import com.Kaushik.Exchange.utils.constants.OrderConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageToEngine {
    private String type;
    private Object data;

    public MessageToEngine() {
        this.type = type;
        this.data = data;
    }

    // Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        System.out.println("Setting message type: " + type);
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        System.out.println("Setting message data: " + data);
        this.data = data;
    }
}