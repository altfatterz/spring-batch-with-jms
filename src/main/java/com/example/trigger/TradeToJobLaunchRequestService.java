package com.example.trigger;

import com.example.model.Trade;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.messaging.Message;

public class TradeToJobLaunchRequestService {

    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public JobLaunchRequest toRequest(Message<Trade> message) {

        Trade trade = message.getPayload();

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString("stock", trade.getStock())
                .addString("action", String.valueOf(trade.getAction()))
                .addLong("quantity", Long.valueOf(trade.getQuantity()));

        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
