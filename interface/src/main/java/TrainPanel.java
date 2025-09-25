import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;


public class TrainPanel extends JPanel {
    private final java.util.List<Train> trains;
    private final List<TrackPath> tracks;
//    private int x = 0;

    public TrainPanel(List<Train> trains, List<TrackPath> tracks) {
        this.trains = trains;
        this.tracks = tracks;
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);

        // Repaint every 50ms
        Timer timer = new Timer(50, e -> repaint());
        timer.start();
    }

    //r2
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(Color.GRAY);
//        g.fillRect(0, 150, getWidth(), 20); // the track
//
//        g.setColor(Color.RED);
//        for (Train train : trains) {
//            g.fillRect(train.getX(), train.getY(), 40, 20); // draw train as rectangle
//        }
//    }

    //r1
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(Color.GRAY);
//        g.fillRect(0, 90, getWidth(), 20); // the track
//
//        g.setColor(Color.RED);
//        g.fillRect(x, 80, 40, 20); // the train
//    }


//    //r3
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        // Draw all trains
//        g.setColor(Color.RED);
//        for (Train train : trains) {
//            Point p = train.getCurrentPosition();
//            if (p != null) {
//                g.fillRect(p.x - 10, p.y - 10, 20, 20);
//            }
//        }
//    }

    //r4
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw tracks
        g.setColor(Color.GRAY);
        for (TrackPath track : tracks) {
            List<Point> points = track.getPoints();
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }


        for (Train train : trains) {
            Point p = train.getCurrentPosition();
            if (p != null) {
                // Draw trains
                g.setColor(Color.RED);
                g.fillRect(p.x - 10, p.y - 10, 20, 20);

                // Train label
                g.setColor(Color.BLACK);
                g.drawString(train.getName(), p.x - 10, p.y - 15);
            }
        }
    }
}
