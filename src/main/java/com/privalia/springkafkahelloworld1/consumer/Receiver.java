package com.privalia.springkafkahelloworld1.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    public static ArrayList<String> receiveMessagesList = new ArrayList<>();

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topic.helloworld}")
    public void recieve(String payload){
        logger.info("received payload='{}'", payload);
        receiveMessagesList.add(payload);
        latch.countDown();
    }
}
