package train_interface;

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

            TrackSection inboundSection = new TrackSection("Inbound");
            TrackSection platformASection = new TrackSection("Platform A");
            TrackSection platformBSection = new TrackSection("Platform B");
            TrackSection outboundSection = new TrackSection("Outbound");
            List<TrackSection> sectionsA = List.of(inboundSection, platformASection, outboundSection);
            List<TrackSection> sectionsB = List.of(outboundSection, platformBSection, inboundSection);

            
            // Left-to-right train
            TrackPath fullPathA = TrackPath.concat(
                    TrackBuilder.createTrack(TrackBuilder.createSegment1Path()),
                    TrackBuilder.createTrack(TrackBuilder.createPlatformAPath()),
                    TrackBuilder.createTrack(TrackBuilder.createSegment2Path())
            );


//            // Right-to-left train (reverse the path!)
//            TrackPath fullPathB = TrackPath.concat(
//                    TrackBuilder.reverse(TrackBuilder.createTrack(TrackBuilder.createSegment2Path())),
//                    TrackBuilder.reverse(TrackBuilder.createTrack(TrackBuilder.createPlatformBPath())),
//                    TrackBuilder.reverse(TrackBuilder.createTrack(TrackBuilder.createSegment1Path()))
//            );
            // Right-to-left train (reverse the path!)
            TrackPath fullPathB = TrackPath.concat(
                    (TrackBuilder.createTrack(TrackBuilder.createSegment2Path())),
                    (TrackBuilder.createTrack(TrackBuilder.createPlatformBPath())),
                    (TrackBuilder.createTrack(TrackBuilder.createSegment1Path()))
            );

            Map<String, TrackPath> routes = new HashMap<>();
            routes.put("routeA", fullPathA);
            routes.put("routeB", fullPathB);


//            train_interface.Train train1 = new train_interface.Train("train_interface.Train A", fullPathA, sectionsA, 100, interface2.Direction.OUT);
//            train_interface.Train train2 = new train_interface.Train("train_interface.Train B", fullPathB, sectionsB, 100, interface2.Direction.IN);
//
//            List<train_interface.Train> trains = asList(train1, train2);
//            List<train_interface.TrackPath> tracks = asList(fullPathA, fullPathB);
//
//            // GUI
//            train_interface.TrainPanel panel = new train_interface.TrainPanel(trains, tracks);
//            JFrame frame = new JFrame("train_interface.Train Branching and Merging");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.add(panel);
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//
//            // Start trains
//            new Thread(train1).start();
//            new Thread(train2).start();

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
            TrainCommandKafkaConsumer consumer = new TrainCommandKafkaConsumer(panel, tracks, routes);
            consumer.startListening();

        });
    }
}


