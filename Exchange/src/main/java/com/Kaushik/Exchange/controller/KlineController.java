package com.Kaushik.Exchange.controller;
import com.Kaushik.Exchange.dto.KlineResponse;
import com.Kaushik.Exchange.service.KlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/klines")
public class KlineController {

    private final KlineService klineService;

    @Autowired
    public KlineController(KlineService klineService) {
        this.klineService = klineService;
        System.out.println("KlineController initialized.");
    }

    @GetMapping
    public ResponseEntity<?> getKlines(
            @RequestParam String market,
            @RequestParam String interval,
            @RequestParam long startTime,
            @RequestParam long endTime) {

        try {
            List<KlineResponse> klines = klineService.getKlines(market, interval, startTime, endTime);
            return ResponseEntity.ok(klines);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error fetching klines: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
