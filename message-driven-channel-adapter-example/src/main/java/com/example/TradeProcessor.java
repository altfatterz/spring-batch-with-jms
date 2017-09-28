package com.example;

import com.example.model.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@MessageEndpoint
public class TradeProcessor {

    private Job job;

    @Autowired
    public TradeProcessor(Job job) {
        this.job = job;
    }

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public JobLaunchRequest process(Trade trade) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString("stock", trade.getStock())
                .addString("action", trade.getAction().toString())
                .addLong("quantity", Long.valueOf(trade.getQuantity()));

        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
