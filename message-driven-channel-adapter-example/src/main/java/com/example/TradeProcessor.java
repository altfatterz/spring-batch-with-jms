package com.example;

import com.example.model.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@MessageEndpoint
public class TradeProcessor {

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public Trade process(Trade trade) {
        trade.setStatus("PROCESSED");
        return trade;
    }
}
