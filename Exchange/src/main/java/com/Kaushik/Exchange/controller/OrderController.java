package com.Kaushik.Exchange.controller;

import com.Kaushik.Exchange.dto.MessageFromOrderbook;
import com.Kaushik.Exchange.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        System.out.println("OrderController initialized.");
    }

    @PostMapping
    public CompletableFuture<MessageFromOrderbook> createOrder(@RequestBody Map<String, Object> request) {
        System.out.println("Received create order request: " + request);
        return orderService.createOrder(
                request.get("market").toString(),
                request.get("price").toString(),
                request.get("quantity").toString(),
                request.get("side").toString(),
                request.get("userId").toString()
        );
    }
    @DeleteMapping
    public CompletableFuture<MessageFromOrderbook>  cancelOrder(@RequestBody Map<String, Object> request) {
        System.out.println("Received cancel order request: " + request);
        return orderService.cancelOrder(
                request.get("orderId").toString(),
                request.get("market").toString()
        );
    }

    @GetMapping("/open")
    public CompletableFuture<MessageFromOrderbook>  getOpenOrders(@RequestParam String userId, @RequestParam String market) {
        System.out.println("Received get open orders request: userId=" + userId + ", market=" + market);
        return orderService.getOpenOrders(userId, market);
    }
}
