package train_interface;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.awt.Point;
import java.util.Properties;
import java.util.UUID;

public class KafkaPositionProducer {
    private final KafkaProducer<String, String> producer;

    public KafkaPositionProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        //props.put("group.id", "train-command-group");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("group.id", UUID.randomUUID().toString()); // it restarts
        props.put("auto.offset.reset", "earliest");

        producer = new KafkaProducer<>(props);
    }

    public void sendPosition(String trainId, Point position) {
        String json = String.format("{\"trainId\":\"%s\", \"x\":%d, \"y\":%d}", trainId, position.x, position.y);
        producer.send(new ProducerRecord<>("train-positions", trainId, json));
    }

    public void close() {
        producer.close();
    }
}