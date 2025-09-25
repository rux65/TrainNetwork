import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class MainInterface {


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            List<Train> trains = new ArrayList<>();
//
//            // Create a few trains
//            trains.add(new Train(0, 140, 5));  // y=140 so it aligns with the track
//            trains.add(new Train(0, 170, 3));
//
//            // Create panel
//            TrainPanel panel = new TrainPanel(trains);
//            JFrame frame = new JFrame("Train Simulation");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.add(panel);
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//
//            // Start each train on its own thread
//            for (Train train : trains) {
//                new Thread(train).start();
//            }
//        });
//    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            // Build track paths
//            TrackPath platformA = TrackBuilder.createPlatformAPath();
//            TrackPath platformB = TrackBuilder.createPlatformBPath();
//
//            // Create trains on different routes
//            Train train1 = new Train(platformA);
//            Train train2 = new Train(platformB);
//
//            List<Train> trains = Arrays.asList(train1, train2);
//
//            // tracks
//            List<TrackPath> tracks = Arrays.asList(platformA, platformB);
//
//
//            // GUI
//            TrainPanel panel = new TrainPanel(trains, tracks);
//            JFrame frame = new JFrame("Train Split Track Simulation");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.add(panel);
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//
//            // Start trains
//            new Thread(train1).start();
//            new Thread(train2).start();
//        });
//    }

    SwingUtilities.invokeLater(() -> {

        // Track segments
//        TrackPath inbound = TrackBuilder.createInboundTrack();
//        TrackPath platformA = TrackBuilder.createPlatformAPath();
//        TrackPath platformB = TrackBuilder.createPlatformBPath();
//        TrackPath outbound = TrackBuilder.createOutboundTrack();
        // Define track sections (shared between trains)
//        TrackSection inbound = new TrackSection("Inbound");
//        TrackSection outbound = new TrackSection("Outbound");
       // TrackSection splitA = new TrackSection("Platform A");
        //TrackSection splitB = new TrackSection("Platform B");

        //TrackPath platformA = TrackBuilder.createPlatformAPath();
        //TrackPath platformB = TrackBuilder.reverse(TrackBuilder.createPlatformBPath());
        //TrackPath inbound = TrackBuilder.createInboundTrackFromPlatform(platformA);
        //TrackPath outbound = TrackBuilder.createOutboundTrackFromPlatform(TrackBuilder.reverse(platformB));

        //TrackPath fullPath = TrackPath.concat(inbound, platformA, outbound);



        // Full paths (combined)
//        TrackPath fullPathA = TrackPath.concat(inbound, platformA, outbound);
//        TrackPath fullPathB = TrackPath.concat(inbound, platformB, outbound);

//        // Create trains
//        Train train1 = new Train(fullPathA); // goes through top branch
//        Train train2 = new Train(fullPathB); // goes through bottom branch

        // Left-to-right train
        TrackPath fullPathA = TrackPath.concat(
                TrackBuilder.createInboundTrackFromPlatform(TrackBuilder.createPlatformAPath())//,
                //TrackBuilder.createPlatformAPath(),
                //TrackBuilder.createOutboundTrackFromPlatform(TrackBuilder.createPlatformAPath())
        );

// Right-to-left train (reverse the path!)
//        TrackPath fullPathB = TrackPath.concat(
//                //TrackBuilder.reverse(TrackBuilder.createOutboundTrack()),
////                TrackBuilder.reverse
////                        (
//                                TrackBuilder.createPlatformBPath()//)//,
////                TrackBuilder.reverse(TrackBuilder.createInboundTrack())
//        );
        TrackPath fullPathB = TrackPath.concat(
                TrackBuilder.createInboundTrackFromPlatform(TrackBuilder.reverse(TrackBuilder.createPlatformBPath()))
                //TrackBuilder.createPlatformBPath()//,
                //TrackBuilder.createOutboundTrackFromPlatform(TrackBuilder.createPlatformBPath())
        );


        // Train section assignments (must match the path segments)
//        List<TrackSection> sectionsA = asList(inbound, splitA, outbound);
//        List<TrackSection> sectionsB = List.of(outbound, splitB, inbound); // reversed order

        TrackSection inboundSection  = new TrackSection("Inbound");
        TrackSection platformASection = new TrackSection("Platform A");
        TrackSection platformBSection = new TrackSection("Platform B");
        TrackSection outboundSection = new TrackSection("Outbound");
        List<TrackSection> sectionsA = List.of(inboundSection, platformASection, outboundSection);
        List<TrackSection> sectionsB = List.of(inboundSection, platformBSection, outboundSection);

        Train train1 = new Train("Train A", fullPathA, sectionsA, 100);
        Train train2 = new Train("Train B", fullPathB, sectionsB, 100);

        List<Train> trains = asList(train1, train2);
        List<TrackPath> tracks = asList(
//                inbound,
//                splitA, splitB
//                platformA, platformB,
//                outbound
                fullPathA,
                fullPathB
        );

        // GUI
        TrainPanel panel = new TrainPanel(trains, tracks);
        JFrame frame = new JFrame("Train Branching and Merging");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start trains
        new Thread(train1).start();
        new Thread(train2).start();
    });
}
}


