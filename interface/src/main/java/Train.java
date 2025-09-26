import java.awt.Point;
import java.util.List;

public class Train implements Runnable {

    private final TrackPath path;
    private int positionIndex = 0;
    private final List<TrackSection> trackSections;
    private final int speed;
    private final String name;
    private final Direction direction;

    public Train(String name, TrackPath path, List<TrackSection> trackSections, int speed, Direction direction) {
        this.name = name;
        this.path = path;
        this.trackSections = trackSections;
        this.speed = speed;
        this.direction = direction;
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
                    trackSections.get(newSectionIndex).enter(this.name);
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
