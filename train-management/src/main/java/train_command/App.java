package train_command;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;
import train_interface.TrainRun;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Hello world!Direction
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("SLF4J Logger: " + LoggerFactory.getILoggerFactory().getClass());

        String trainPositionTopic = "train-positions";


        // Start the consumer
        TrainMonitor.startConsumer(trainPositionTopic);

        // Common Kafka producer config
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("auto.offset.reset", "earliest");
        kafkaProps.put("group.id", UUID.randomUUID().toString());

        TrainRun.runTrains();
        Thread.sleep(3000);
        sendTrainCommands();
    }

    static void sendTrainCommands() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);


        //Gson gson = new Gson();
        TrainBE trainA = new TrainBE("Train A", "routeA", 100, TrainBE.Direction.LEFT);
        TrainBE trainB = new TrainBE("Train B", "routeB", 70, TrainBE.Direction.RIGHT);

        //train_command.TrainCommandMessage message = new train_command.TrainCommandMessage(List.of(trainA, trainB));

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
