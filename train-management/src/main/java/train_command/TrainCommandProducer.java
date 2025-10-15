package train_command;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class TrainCommandProducer {

    private final KafkaProducer<String, String> producer;

    public TrainCommandProducer() {

        // Common Kafka producer config
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("auto.offset.reset", "earliest");
        kafkaProps.put("group.id", UUID.randomUUID().toString());
        kafkaProps.put("auto.offset.reset", "earliest");

        producer = new KafkaProducer<>(kafkaProps);
    }

    void sendTrainCommands() {
        //Gson gson = new Gson();
        TrainBE trainA = new TrainBE("Train A", "routeA", 100, TrainBE.Direction.LEFT);
        TrainBE trainB = new TrainBE("Train B", "routeB", 70, TrainBE.Direction.RIGHT);

        String json = TrainBE.toJsonList(List.of(trainA, trainB));
        System.out.println(json);

        ProducerRecord<String, String> record = new ProducerRecord<>("train-control", json);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("Message sent to topic: " + metadata.topic());
            }
        });
        producer.flush();
        producer.close();
    }
}
