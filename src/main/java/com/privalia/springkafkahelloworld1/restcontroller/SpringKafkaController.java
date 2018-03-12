package com.privalia.springkafkahelloworld1.restcontroller;

import com.privalia.springkafkahelloworld1.consumer.Receiver;
import com.privalia.springkafkahelloworld1.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kafka")
public class SpringKafkaController {

    @Autowired
    private Sender sender;

    @Value("${kafka.topic.helloworld}")
    private String topic;

    @RequestMapping(method = RequestMethod.GET)
    public String sendMessage(@RequestParam(value = "message", required = false, defaultValue = "Hello From WebsService") String message) {
        sender.send(topic, message);
        return "Message: " + message;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/receive")
    public List<String> getReceiveMessages(){
        ArrayList<String> receiveMessagesList = Receiver.receiveMessagesList;
        return receiveMessagesList;
    }

}
