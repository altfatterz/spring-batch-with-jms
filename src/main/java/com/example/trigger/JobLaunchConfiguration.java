package com.example.trigger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.jms.JmsDestinationPollingSource;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableIntegration
public class JobLaunchConfiguration {

    @Autowired
    private JobLaunchingGateway jobLaunchingGateway;

    @Autowired
    private JmsDestinationPollingSource jmsDestinationPollingSource;

    @Autowired
    private Job job;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Bean
    public JmsDestinationPollingSource jmsDestinationPollingSource() {
        JmsDestinationPollingSource jmsDestinationPollingSource = new JmsDestinationPollingSource(jmsTemplate);
        return jmsDestinationPollingSource;
    }

    @Bean
    public IntegrationFlow myDatabaseTriggeredFlow() {
        return IntegrationFlows.from(jmsDestinationPollingSource,
                c -> c.poller(Pollers.fixedRate(5000, 2000)))
                .handle(jobLaunchingGateway)
                .handle(logger())
                .get();
    }

    @Bean
    TriggerMessageToJobLaunchRequestService toJobLaunchRequest() {
        TriggerMessageToJobLaunchRequestService transformService = new TriggerMessageToJobLaunchRequestService();
        transformService.setJob(job);
        return transformService;
    }

    @Bean
    JobLaunchingGateway jobLaunchingGateway(JobLauncher jobLauncher) {
        return new JobLaunchingGateway(jobLauncher);
    }

    @Bean
    LoggingHandler logger() {
        return new LoggingHandler("INFO");
    }

}
