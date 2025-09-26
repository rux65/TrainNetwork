import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TrackPath {
    private final List<Point> points;

    public List<TrackSection> getSections() {
        return sections;
    }

    private List<TrackSection> sections;

    public TrackPath(List<Point> points, List<TrackSection> sections) {
        this.points = points;
        this.sections=sections;
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
        List<TrackSection> sections = new ArrayList<>();
        for (TrackPath path : paths) {
            result.addAll(path.getPoints());
            sections.addAll(path.getSections());
        }
        return new TrackPath(result, sections);
    }
}