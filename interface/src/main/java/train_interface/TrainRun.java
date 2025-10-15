package train_interface;

import kafka_prod_consumer.TrainCommandKafkaConsumer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class TrainRun {

    public static void runTrains(){

        SwingUtilities.invokeLater(() -> {

            //todo ideally we separate straight lines and branches into platform sections
            
            // Left-to-right train
            TrackPath fullPathA = TrackPath.concat(
                    TrackBuilder.createTrack(TrackBuilder.createSegment1Path()),
                    TrackBuilder.createTrack(TrackBuilder.createPlatformAPath()),
                    TrackBuilder.createTrack(TrackBuilder.createSegment2Path())
            );

            // Right-to-left train (reverse the path!)
            TrackPath fullPathB = TrackPath.concat(
                    (TrackBuilder.createTrack(TrackBuilder.createSegment2Path())),
                    (TrackBuilder.createTrack(TrackBuilder.createPlatformBPath())),
                    (TrackBuilder.createTrack(TrackBuilder.createSegment1Path()))
            );

            Map<String, TrackPath> routes = new HashMap<>();
            routes.put("routeA", fullPathA);
            routes.put("routeB", fullPathB);

            List<TrackPath> tracks = asList(fullPathA, fullPathB);
            List<Train> trains = new ArrayList<>(); // initially empty

            TrainPanel panel = new TrainPanel(trains, tracks);
            JFrame frame = new JFrame("train_interface.Train Branching and Merging");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Start Kafka consumer to control train creation
            // this will listen on what train comes on which track
            TrainCommandKafkaConsumer consumer = new TrainCommandKafkaConsumer(panel, tracks, routes);
            consumer.startListening();
        });
    }
}


