package com.example.trigger;

import com.example.model.Trade;
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
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableIntegration
public class JobLaunchConfiguration {

    @Autowired
    private JobLaunchingGateway jobLaunchingGateway;

    @Autowired
    private JmsDestinationPollingSource jmsDestinationPollingSource;

    @Autowired
    private Job job;

    @Bean
    public JmsDestinationPollingSource jmsDestinationPollingSource(JmsTemplate jmsTemplate) {
        jmsTemplate.setMessageConverter(messageConverter());

        JmsDestinationPollingSource jmsDestinationPollingSource = new JmsDestinationPollingSource(jmsTemplate);
        jmsDestinationPollingSource.setDestinationName("jobtrigger");
        return jmsDestinationPollingSource;
    }

    @Bean
    public IntegrationFlow myJmsTriggeredFlow() {
        return IntegrationFlows.from(jmsDestinationPollingSource,
                c -> c.poller(Pollers.fixedRate(5000, 2000)))
                .transform(toJobLaunchRequest())
                .handle(jobLaunchingGateway)
                .handle(logger())
                .get();
    }

    @Bean
    TradeToJobLaunchRequestService toJobLaunchRequest() {
        TradeToJobLaunchRequestService transformService = new TradeToJobLaunchRequestService();
        transformService.setJob(job);
        return transformService;
    }

    @Bean
    @SuppressWarnings("Duplicates")
    MessageConverter messageConverter() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("trade", Trade.class);
        marshaller.setAliases(aliases);

        MarshallingMessageConverter messageConverter = new MarshallingMessageConverter(marshaller);
        messageConverter.setTargetType(MessageType.TEXT);
        return messageConverter;
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
