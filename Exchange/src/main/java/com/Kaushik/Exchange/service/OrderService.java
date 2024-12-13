package com.Kaushik.Exchange.service;

import com.Kaushik.Exchange.dto.MessageFromOrderbook;
import com.Kaushik.Exchange.dto.MessageToEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {

    private final RedisManager redisManager;

    @Autowired
    public OrderService(RedisManager redisManager) {
        this.redisManager = redisManager;
        System.out.println("OrderService initialized.");
    }

    public MessageFromOrderbook createOrder(String market, String price, String quantity, String side, String userId) {
        System.out.println("Creating order: market=" + market + ", price=" + price + ", quantity=" + quantity + ", side=" + side + ", userId=" + userId);
        MessageToEngine message = new MessageToEngine();
        message.setType("CREATE_ORDER");
        message.setData(Map.of(
                "market", market,
                "price", price,
                "quantity", quantity,
                "side", side,
                "userId", userId
        ));
        return redisManager.sendAndAwait(message);
    }
    public MessageFromOrderbook cancelOrder(String orderId, String market) {
        System.out.println("Cancelling order: orderId=" + orderId + ", market=" + market);
        MessageToEngine message = new MessageToEngine();
        message.setType("CANCEL_ORDER");
        message.setData(Map.of(
                "orderId", orderId,
                "market", market
        ));
        return redisManager.sendAndAwait(message);
    }

    public MessageFromOrderbook getOpenOrders(String userId, String market) {
        System.out.println("Fetching open orders: userId=" + userId + ", market=" + market);
        MessageToEngine message = new MessageToEngine();
        message.setType("GET_OPEN_ORDERS");
        message.setData(Map.of(
                "userId", userId,
                "market", market
        ));
        return redisManager.sendAndAwait(message);
    }
}
