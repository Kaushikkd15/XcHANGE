package com.Kaushik.Exchange.controller;

import com.Kaushik.Exchange.model.Order;
import com.Kaushik.Exchange.service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class orderController {

    @Autowired
    private orderService orderService;




    @PostMapping
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Order order){
        Map<String, Object> result = orderService.fillOrder(order);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
