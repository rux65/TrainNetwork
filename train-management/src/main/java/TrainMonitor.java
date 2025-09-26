import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

// consumer
//public class TrainMonitor {
//
//    public static void startConsumer(String topic) {
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Adjust if needed
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "train-monitor-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        consumer.subscribe(Collections.singletonList(topic));
//        System.out.println("Kafka consumer subscribed to topic: " + topic);
//
//        new Thread(() -> {
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
////                for (ConsumerRecord<String, String> record : records) {
////                    System.out.println("[Monitor] Received: " + record.value());
////                }
//                for (ConsumerRecord<String, String> record : records) {
//                    String trainId = record.key();
//                    String positionJson = record.value();
//
//                    // Log or handle the position update
//                    System.out.printf("Train %s reported position: %s%n", trainId, positionJson);
//                }
//            }
//        }).start();
//    }
//}

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
                        System.out.printf("Train %s reported position: %s%n", record.key(), record.value());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

