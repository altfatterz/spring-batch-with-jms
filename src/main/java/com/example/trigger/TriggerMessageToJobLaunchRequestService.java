package com.example.trigger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.messaging.Message;

import java.util.List;

public class TriggerMessageToJobLaunchRequestService {

    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public JobLaunchRequest toRequest(Message<List<TriggerMessage>> message) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        // currently assuming only one entry is added to the trigger table
        TriggerMessage triggerMessage = message.getPayload().get(0);

        // from the trigger table entry we can set job parameters
        jobParametersBuilder.addString("trigger_date", triggerMessage.getDate().toString());

        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
