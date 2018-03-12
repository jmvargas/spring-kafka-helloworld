package com.privalia.springkafkahelloworld1;

import com.privalia.springkafkahelloworld1.consumer.Receiver;
import com.privalia.springkafkahelloworld1.producer.Sender;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaHelloworld1ApplicationTests {

	protected final static String HELLOWORLD_TOPIC = "test.t";

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@Autowired
	private Receiver receiver;

	@Autowired
	private Sender sender;

	@ClassRule
	public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1, true, HELLOWORLD_TOPIC);

	@Before
	public void runBeforeTestMethod() throws Exception {
		// wait until all the partitions are assigned
		for(MessageListenerContainer messageListenerContainer: kafkaListenerEndpointRegistry.getListenerContainers()){
			ContainerTestUtils.waitForAssignment(messageListenerContainer, kafkaEmbedded.getPartitionsPerTopic());
		}
	}

	@Test
	public void testReceive() throws InterruptedException {
		sender.send(HELLOWORLD_TOPIC, "Hello Spring Kafka!");
		receiver.getLatch().await(10, TimeUnit.SECONDS);
		assertThat(receiver.getLatch().getCount()).isEqualTo(0);
		assertThat(Receiver.receiveMessagesList.size()).isEqualTo(1);
	}

}
