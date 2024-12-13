package com.Kaushik.Exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class KlineResponse {
    private double close;
    private Timestamp end;
    private double high;
    private double low;
    private double open;
    private double quoteVolume;
    private Timestamp start;
    private int trades;
    private double volume;

    public KlineResponse(double close, Timestamp end, double high, double low, double open,
                         double quoteVolume, Timestamp start, int trades, double volume) {
        this.close = close;
        this.end = end;
        this.high = high;
        this.low = low;
        this.open = open;
        this.quoteVolume = quoteVolume;
        this.start = start;
        this.trades = trades;
        this.volume = volume;
    }

}

