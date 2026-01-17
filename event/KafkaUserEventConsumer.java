package examples.events;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

public class KafkaUserEventConsumer {

    private final KafkaConsumer<String, String> consumer;

    public KafkaUserEventConsumer(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public String waitForUserCreatedEvent(String userId) {
        consumer.subscribe(Collections.singletonList("user.created"));

        long timeout = System.currentTimeMillis() + 5000;

        while (System.currentTimeMillis() < timeout) {
            for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofMillis(200))) {
                if (record.value().contains(userId)) {
                    return record.value();
                }
            }
        }
        return null;
    }
}
