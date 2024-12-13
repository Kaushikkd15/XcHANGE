package com.Kaushik.Exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class OrderRequest {
    private String market;
    private Double price;
    private Double quantity;
    private String side;
    private String userId;
}
