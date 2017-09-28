package com.example;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class TradeProcessor {

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public String upperCase(String input) {
        return "JMS response: " + input.toUpperCase();
    }
}
