package com.example.trigger2;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class TradeServiceImpl {

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public String upperCase(String input) {
        return "JMS response: " + input.toUpperCase();
    }
}
