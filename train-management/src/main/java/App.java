import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
//    public class ParallelTrains {
//
//        public static void main(String[] args) {
//            int numberOfTrains = 5;
//
//            ExecutorService executor = Executors.newFixedThreadPool(numberOfTrains);
//
//            for (int i = 1; i <= numberOfTrains; i++) {
//                final int trainNumber = i;
//                executor.submit(() -> {
//                    System.out.println("Train " + trainNumber + " started on " + Thread.currentThread().getName());
//                    // Simulate work
//                    try {
//                        Thread.sleep(2000); // simulate train doing work
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                    System.out.println("Train " + trainNumber + " finished");
//                });
//            }
//
//            executor.shutdown(); // Gracefully shutdown after all trains finish
//        }
//    }

    public static void main(String[] args) {
        System.out.println("SLF4J Logger: " + LoggerFactory.getILoggerFactory().getClass());

        String topic = "train-positions";

        // Start the consumer
        TrainMonitor.startConsumer(topic);

        // Common Kafka producer config
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        int numberOfTrains = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTrains);

        for (int i = 1; i <= numberOfTrains; i++) {
            executor.submit(new Train(i, topic, kafkaProps));
        }

        executor.shutdown();
    }
}
