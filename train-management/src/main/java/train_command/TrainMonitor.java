package train_command;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

// consumer
public class TrainMonitor {
    public static void startConsumer(String topic) {
        new Thread(() -> {
            System.out.println("Initializing Kafka consumer...");

            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", UUID.randomUUID().toString());
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("auto.offset.reset", "earliest");

            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
                consumer.subscribe(Collections.singletonList(topic));
                System.out.println("Kafka consumer subscribed to topic: " + topic);

                while (true) {
                    System.out.println("Polling for messages...");
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    System.out.println("count "+records.count());
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf("train_interface.Train %s reported position: %s%n", record.key(), record.value());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

