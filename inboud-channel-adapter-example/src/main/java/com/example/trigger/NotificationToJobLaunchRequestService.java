package com.example.trigger;

import com.example.model.Notification;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.messaging.Message;

public class NotificationToJobLaunchRequestService {

    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public JobLaunchRequest toRequest(Message<Notification> message) {

        Notification notification = message.getPayload();

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString("email", notification.getEmail())
                .addString("status", notification.getStatus());

        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
