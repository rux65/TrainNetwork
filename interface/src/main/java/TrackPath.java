import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackPath {
    private final List<Point> points;

    public TrackPath(List<Point> points) {
        this.points = points;
    }

    public Point getPoint(int index) {
        if (index >= points.size()) return null;
        return points.get(index);
    }

    public int length() {
        return points.size();
    }

    public List<Point> getPoints() {
        return points;
    }

    public static TrackPath concat(TrackPath... paths) {
        List<Point> result = new ArrayList<>();
        for (TrackPath path : paths) {
            result.addAll(path.getPoints());
        }
        return new TrackPath(result);
    }
}