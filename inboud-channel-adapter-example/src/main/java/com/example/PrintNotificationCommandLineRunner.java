package com.example;

import com.example.model.Notification;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PrintNotificationCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        log.info("marshalling Notification");

        Notification notification = Notification.builder()
                .email("example@gmail.com")
                .status("ORDER_DISPATCHED")
                .build();

        XStream xStream = new XStream();
        xStream.alias("notification", Notification.class);

        log.info("\n{}\n", xStream.toXML(notification));
    }
}
