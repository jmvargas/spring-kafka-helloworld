package com.privalia.springkafkahelloworld1;

import com.privalia.springkafkahelloworld1.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringKafkaHelloworld1Application implements CommandLineRunner {

	@Autowired
	private Sender sender;

	@Value("${kafka.topic.helloworld}")
	private String topic;

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaHelloworld1Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		sender.send(topic, "Hello world tio!");
	}
}
