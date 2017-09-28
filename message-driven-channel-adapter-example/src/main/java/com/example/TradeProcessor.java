package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@MessageEndpoint
public class TradeProcessor {

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public String process(String input) {
        return input;
    }
}
