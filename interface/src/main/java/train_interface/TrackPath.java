package train_interface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackPath {
    private final List<Point> points;
    private List<TrackSection> sections;
    private Set<Point> boundaries;

    public TrackPath(List<Point> points, List<TrackSection> sections) {
        this.points = points;
        this.sections=sections;
        this.boundaries=Set.of(points.get(0), points.get(points.size()-1));
    }

    public TrackPath(List<Point> points, List<TrackSection> sections, Set<Point> boundaries) {
        this.points = points;
        this.sections=sections;
        this.boundaries=boundaries;
    }

    public Set<Point> getBoundaries() {
        return boundaries;
    }

    public List<TrackSection> getSections() {
        return sections;
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
        Set<Point> boundaries = new HashSet<>();
        for (TrackPath path : paths) {
            result.addAll(path.getPoints());
            sections.addAll(path.getSections());
            boundaries.addAll(path.getBoundaries());
        }
        result = result.stream().sorted(Comparator.comparing(Point::getX)).collect(Collectors.toList());
        return new TrackPath(result, sections,boundaries);
    }

    public static TrackPath reverse(TrackPath original) {
        List<Point> reversed = new ArrayList<>(original.getPoints());
        Collections.reverse(reversed);
        List<TrackSection> sections = original.getSections();
        Collections.reverse(sections);
        return new TrackPath(reversed, sections, original.getBoundaries());
    }
}