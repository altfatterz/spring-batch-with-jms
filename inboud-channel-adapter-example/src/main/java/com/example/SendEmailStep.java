package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.batch.api.Batchlet;

import static org.springframework.batch.core.ExitStatus.COMPLETED;

@Component
@StepScope
@Slf4j
public class SendEmailStep implements Batchlet {

    public JavaMailSender emailSender;

    private String email;
    private String status;

    public SendEmailStep(JavaMailSender emailSender,
                         @Value("#{jobParameters['email']}") String email,
                         @Value("#{jobParameters['status']}") String status) {

        this.emailSender = emailSender;
        this.email = email;
        this.status = status;
    }

    @Override
    public String process() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("test notification");
        message.setText("You have been notified about the status: " + status);

        emailSender.send(message);

        log.info("Sent email to {} address", email);

        return COMPLETED.toString();
    }

    @Override
    public void stop() throws Exception {

    }
}
