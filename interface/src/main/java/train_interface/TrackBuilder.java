package train_interface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackBuilder {

    static List<TrackPath> straightElements = new ArrayList<>();
    static List<TrackPath> platformElements = new ArrayList<>();

    public List<TrackPath> getPlatformElements() {
        return platformElements;
    }

    public List<TrackPath> getStraightElements() {
        return straightElements;
    }

    //so these are my reference points
    public static TrackPath createPlatformAPath() {
        List<Point> points = new ArrayList<>();
        //  -  - - -   -
        //     \ _ /

        // x = 0, y =0 straight line increase and decrease

        points.add(new Point(250, 100));
        points.add(new Point(300, 100)); // platform
        points.add(new Point(350, 100)); // platform
        points.add(new Point(400, 100)); // platform
        points.add(new Point(450, 100));

        return new TrackPath(points, List.of(new TrackSection("Platform A")), new HashSet<>(points));
    }

    public static TrackPath createPlatformBPath() {
        List<Point> points = new ArrayList<>();
        // platform B
        points.add(new Point(250, 100));
        points.add(new Point(300, 150)); // platform area
        points.add(new Point(350, 150)); // platform area
        points.add(new Point(400, 150)); // platform area
        points.add(new Point(450, 100));

        return new TrackPath(points, List.of(new TrackSection("Platform B")), new HashSet<>(points));
    }

    public static TrackPath createSegment1Path() {
        List<Point> points = new ArrayList<>();
        // platform B
        points.add(new Point(200, 100));
        points.add(new Point(250, 100));

        return new TrackPath(points, List.of(new TrackSection("Segment 1")), new HashSet<>(points));
    }

    public static TrackPath createSegment2Path() {
        List<Point> points = new ArrayList<>();
        // platform B
        points.add(new Point(450, 100));
        points.add(new Point(500, 100));

        return new TrackPath(points, List.of(new TrackSection("Segment 2")), new HashSet<>(points));
    }


    public static TrackPath createTrack(TrackPath platform) {
        Set<Point> inboundWhole = new HashSet<>();
        Point prevPoint = null;
        for (Point point : platform.getPoints()) {
            ArrayList<Point> inbound = new ArrayList<>();

            //straight line increase
            if (prevPoint != null && point.y == prevPoint.y && prevPoint.x < point.x) {
                // no y change
                for (int i = prevPoint.x; i <= point.x; i += 10) {

                    inbound.add(new Point(i, point.y));
                }
            }
            //straight line descending
            if (prevPoint != null && point.y == prevPoint.y && prevPoint.x > point.x) {
                // no y change
                for (int i = prevPoint.x; i >= point.x; i -= 10) {
                    inbound.add(new Point(i, point.y));
                }
            }

            // x is increasing and y is increasing
            // >-----pr
            //         \
            //          p ----->
            if (prevPoint != null && prevPoint.y < point.y && prevPoint.x < point.x) {
                // y change
                int newY = prevPoint.y;
                for (int i = prevPoint.x; i <= point.x; i += 10) {
                    inbound.add(new Point(i, newY));
                    newY += 10;
                }
            }

            // x is decreasing  and y is increasing
            //        pr -------<
            //       /
            // <----p
            else if (prevPoint != null && prevPoint.y < point.y && prevPoint.x > point.x) {
                int newY = prevPoint.y;
                for (int i = prevPoint.x; i >= point.x; i -= 10) {
                    inbound.add(new Point(i, newY));
                    newY += 10;

                }
            }

            // x is increasing  and y is decreasing
            //        pr ------->
            //       /
            // >----p
            else if (prevPoint != null && prevPoint.y > point.y && prevPoint.x < point.x) {
                int newY = prevPoint.y;
                for (int i = prevPoint.x; i <= point.x; i += 10) {
                    inbound.add(new Point(i, newY));
                    newY -= 10;
                }
            }

            // x is decreasing and y is decreasing
            // <-----pr
            //         \
            //          p -----<
            if (prevPoint != null && prevPoint.y > point.y && prevPoint.x > point.x) {
                int newY = prevPoint.y;
                for (int i = prevPoint.x; i >= point.x; i -= 10) {
                    inbound.add(new Point(i, newY));
                    newY -= 10;
                }
            }

            if (point != prevPoint) {
                prevPoint = point;
            }
            inboundWhole.addAll(inbound);
        }
        List<Point> inbound = inboundWhole.stream().sorted(Comparator.comparing(Point::getX)).collect(Collectors.toList());
        return new TrackPath(inbound, platform.getSections(), platform.getBoundaries());
    }


}
