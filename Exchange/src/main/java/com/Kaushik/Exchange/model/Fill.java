package com.Kaushik.Exchange.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Fill {
    private double price;
    private double qty;
    private int tradeId;

    public Fill(double price, double filledQuantity, int i) {
    }
}