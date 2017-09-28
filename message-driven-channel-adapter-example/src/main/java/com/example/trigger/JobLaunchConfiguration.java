package com.example.trigger;

import com.example.model.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.MessageChannel;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableIntegration
public class JobLaunchConfiguration {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private JobLaunchingGateway jobLaunchingGateway;

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setDestinationName("trades");
        return simpleMessageListenerContainer;
    }

    @Bean
    public JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint() {
        ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener = new ChannelPublishingJmsMessageListener();
        channelPublishingJmsMessageListener.setRequestChannel(inputChannel());
        channelPublishingJmsMessageListener.setMessageConverter(messageConverter());

        JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint = new JmsMessageDrivenEndpoint(messageListenerContainer(),
                channelPublishingJmsMessageListener);

        return jmsMessageDrivenEndpoint;
    }

    @Bean
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
    public IntegrationFlow myFlow() {
        return IntegrationFlows.from("outputChannel")
                .handle(jobLaunchingGateway)
                .handle(logger())
                .get();
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