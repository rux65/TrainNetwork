import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.awt.Point;
import java.util.List;
import java.util.Properties;
import java.util.Random;

// producer
public class Train implements Runnable {
    private final int trainId;
    private final KafkaProducer<String, String> producer;
    private final String topic;
    private final Random random = new Random();
//
//    public Train(int trainId, String topic, Properties kafkaProps) {
//        this.trainId = trainId;
//        this.topic = topic;
//        this.producer = new KafkaProducer<>(kafkaProps);
//    }

//    @Override
//    public void run() {
//        int position = 0;
//        for (int i = 0; i < 10; i++) { // Simulate 10 position updates
//            position += random.nextInt(10) + 1;
//            String message = "Train " + trainId + " at position " + position;
//
//            ProducerRecord<String, String> record = new ProducerRecord<>(topic, Integer.toString(trainId), message);
//            producer.send(record);
//
//            System.out.println("[Train " + trainId + "] Sent: " + message);
//
//            try {
//                Thread.sleep(1000); // Simulate delay
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//
//        producer.close();
//    }

    private final TrackPath path;
    private int positionIndex = 0;
    private final List<TrackSection> trackSections;
    private final int speed;
    private final String name;
    private final Direction direction;


    public Train(int trainId, String topic, Properties kafkaProps, String name, TrackPath path, List<TrackSection> trackSections, int speed, Direction direction) {
        this.name = name;
        this.path = path;
        this.trackSections = trackSections;
        this.speed = speed;
        this.direction = direction;
        this.trainId = trainId;
        this.topic = topic;
        this.producer = new KafkaProducer<>(kafkaProps);
    }

    public synchronized Point getCurrentPosition() {
        return path.getPoint(positionIndex);
    }

    public void move() {
        if (positionIndex < path.length() - 1) {
            positionIndex++;
        }
    }

    public void run() {
        // Assume the path is divided roughly into section-lengths
        int sectionSize = path.length() / trackSections.size();
        int sectionIndex = 0;


        while (positionIndex < path.length() - 1) {
            String message = "Train " + trainId + " at position " + positionIndex;
            // Check if we are entering a new section
            int newSectionIndex = positionIndex / sectionSize;
            if (newSectionIndex != sectionIndex) {
                trackSections.get(sectionIndex).leave();
                trackSections.get(newSectionIndex).enter(this.name);
                sectionIndex = newSectionIndex;
            }
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, Integer.toString(trainId));
            producer.send(record);
            positionIndex++;
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                break;
            }
        }

        // Leave final section
        trackSections.get(sectionIndex).leave();
        System.out.println(name + " has finished.");
    }

    public String getName() {
        return name;
    }
}

