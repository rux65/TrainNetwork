import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.List;

import static java.util.Arrays.asList;

public class MainInterface {

    public static void main(String[] args) {

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
                    TrackBuilder.createTrack(TrackBuilder.createConnectingPathsA()),
                    TrackBuilder.createTrack(TrackBuilder.createPlatformAPath()),
                    TrackBuilder.createTrack(TrackBuilder.createConnectingPathsB())
            );


            // Right-to-left train (reverse the path!)
            TrackPath fullPathB = TrackPath.concat(
                    TrackBuilder.reverse(TrackBuilder.createTrack(TrackBuilder.createConnectingPathsB())),
                    TrackBuilder.reverse(TrackBuilder.createTrack(TrackBuilder.createPlatformBPath())),
                    TrackBuilder.reverse(TrackBuilder.createTrack(TrackBuilder.createConnectingPathsA()))
            );

            Train train1 = new Train("Train A", fullPathA, 100, Direction.OUT);
            Train train2 = new Train("Train B", fullPathB, 100, Direction.IN);

            List<Train> trains = asList(train1, train2);
            List<TrackPath> tracks = asList(fullPathA, fullPathB);

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


