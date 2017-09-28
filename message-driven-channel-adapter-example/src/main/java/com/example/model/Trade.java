package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * An example message:
 *
 * <trade>
 *   <stock>AMZN</stock>
 *   <quantity>100</quantity>
 *   <action>BUY</action>
 * </trade>
 */
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

