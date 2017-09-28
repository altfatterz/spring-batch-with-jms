package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class Trade {

    private String stock;
    private Integer quantity;
    private Action action;

    public static enum  Action {
        SELL, BUY,
    }
}
