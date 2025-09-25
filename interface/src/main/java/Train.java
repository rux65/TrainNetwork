import java.awt.Point;
import java.util.List;

public class Train implements Runnable {
//    private int x;
//    private int y;
//    private final int speed;
//
//    public Train(int startX, int startY, int speed)  {
//        this.x = startX;
//        this.y = startY;
//        this.speed = speed;
//    }
//
//    public synchronized void move() {
//        x += speed;
//    }
//
//    public synchronized int getX() {
//        return x;
//    }
//
//    public synchronized int getY() {
//        return y;
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            move();
//            try {
//                Thread.sleep(100); // adjust for smoother/faster movement
//            } catch (InterruptedException e) {
//                break;
//            }
//        }
//    }


    private final TrackPath path;
    private int positionIndex = 0;
    private final List<TrackSection> trackSections;
    private final int speed;
    private final String name;

    public Train(String name, TrackPath path, List<TrackSection> trackSections, int speed) {
        this.name = name;
        this.path = path;
        this.trackSections = trackSections;
        this.speed = speed;
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
            // Check if we are entering a new section
            int newSectionIndex = positionIndex / sectionSize;
            if (newSectionIndex != sectionIndex) {
                trackSections.get(sectionIndex).leave();
                trackSections.get(newSectionIndex).enter();
                sectionIndex = newSectionIndex;
            }

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
