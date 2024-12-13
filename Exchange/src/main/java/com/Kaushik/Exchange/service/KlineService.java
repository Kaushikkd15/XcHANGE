package com.Kaushik.Exchange.service;

import com.Kaushik.Exchange.dto.KlineResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.util.Map;

@Service
public class KlineService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public KlineService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<KlineResponse> getKlines(String market, String interval, long startTime, long endTime) {
        String query = getQueryForInterval(interval);

        if (query == null) {
            throw new IllegalArgumentException("Invalid interval");
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                query,
                new Timestamp(startTime * 1000),
                new Timestamp(endTime * 1000)
        );

        return rows.stream().map(row -> new KlineResponse(
                (Double) row.get("close"),
                (Timestamp) row.get("bucket"),
                (Double) row.get("high"),
                (Double) row.get("low"),
                (Double) row.get("open"),
                (Double) row.get("quoteVolume"),
                (Timestamp) row.get("start"),
                (Integer) row.get("trades"),
                (Double) row.get("volume")
        )).toList();
    }

    private String getQueryForInterval(String interval) {
        return switch (interval) {
            case "1m" -> "SELECT * FROM klines_1m WHERE bucket >= $1 AND bucket <= $2";
            case "1h" -> "SELECT * FROM klines_1m WHERE  bucket >= $1 AND bucket <= $2";
            case "1w" -> "SELECT * FROM klines_1w WHERE bucket >= $1 AND bucket <= $2";
            default -> null;
        };
    }
}

