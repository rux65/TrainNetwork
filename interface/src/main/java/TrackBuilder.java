
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//public class TrackBuilder {
//    public static TrackPath createMainTrack() {
//        List<Point> points = new ArrayList<>();
//        for (int x = 0; x <= 200; x += 10) points.add(new Point(x, 200));
//        return new TrackPath(points);
//    }
//
//    public static TrackPath createPlatformAPath() {
//        List<Point> points = new ArrayList<>();
//        for (int x = 200; x <= 300; x += 10) points.add(new Point(x, 150));
//        for (int x = 300; x <= 400; x += 10) points.add(new Point(x, 150));
//        for (int x = 400; x <= 500; x += 10) points.add(new Point(x, 200));
//        return new TrackPath(points);
//    }
//
//    public static TrackPath createPlatformBPath() {
//        List<Point> points = new ArrayList<>();
//        for (int x = 200; x <= 300; x += 10) points.add(new Point(x, 250));
//        for (int x = 300; x <= 400; x += 10) points.add(new Point(x, 250));
//        for (int x = 400; x <= 500; x += 10) points.add(new Point(x, 200));
//        return new TrackPath(points);
//    }
//}

public class TrackBuilder {

//    public static TrackPath createInboundTrack() {
//        List<Point> points = new ArrayList<>();
//        for (int x = 0; x <= 200; x += 10)
//            points.add(new Point(x, 200));
//        return new TrackPath(points);
//        //return createPlatformBPath();
//    }

    //so these are my reference points
    public static TrackPath createPlatformAPath() {
        List<Point> points = new ArrayList<>();
        //  -  - - -   -
        //     \ _ /

        // x = 0, y =0 straight line increase and decrease

        //



        points.add(new Point(200, 200));
        points.add(new Point(250, 200));

        points.add(new Point(300, 200)); // platform
        points.add(new Point(350, 200)); // platform
        points.add(new Point(400, 200)); // platform

        points.add(new Point(450, 200));
        points.add(new Point(500, 200));
        List<Point> p = points.stream().map(po -> (Point)po).collect(Collectors.toList());
        return new TrackPath(p);
    }

    public static TrackPath createPlatformBPath() {
        List<Point> points = new ArrayList<>();
        // right angles, isosceles triangles
        points.add(new Point(200, 100));
        points.add(new Point(250, 100));
        points.add(new Point(300, 150)); // platform
        points.add(new Point(350, 150)); // platform
        points.add(new Point(400, 150)); // platform
        points.add(new Point(450, 100));
        points.add(new Point(500, 100));
        return new TrackPath(points);
    }

//    public static TrackPath createOutboundTrack() {
//        List<Point> points = new ArrayList<>();
//        for (int x = 200; x <= 500; x += 10)
//            points.add(new Point(x, 200));
//        return new TrackPath(points);
//        //return createPlatformAPath();
//        //TrackPath inbound = new TrackPath(platformA.getPoints().subList(0, 1));
//    }

    public static TrackPath reverse(TrackPath original) {
        List<Point> reversed = new ArrayList<>(original.getPoints());
        Collections.reverse(reversed);
        return new TrackPath(reversed);
    }

    public static TrackPath createInboundTrackFromPlatform(TrackPath platform) {
        List<Point> inboundWhole = new ArrayList<>();
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
            else if (prevPoint != null && point.y == prevPoint.y && prevPoint.x > point.x) {
                // no y change
                for (int i = prevPoint.x; i >= point.x; i -= 10) {
                    inbound.add(new Point(i, point.y));
                }
            }

            // x is increasing and y is increasing
            else if (prevPoint != null && prevPoint.y <point.y  && prevPoint.x < point.x) {
                // y change
                int newY = prevPoint.y;
                for (int i = prevPoint.x; i <= point.x; i += 10) {
                    inbound.add(new Point(i, newY));
                    newY += 10;
                }
            }

            // x is decreasing  and y is increasing
            else if (prevPoint != null && prevPoint.y >point.y   && prevPoint.x > point.x) {
                int newY = prevPoint.y;
                for (int i = prevPoint.x; i >= point.x; i -= 10) {
                    inbound.add(new Point(i, newY));
                    newY += 10;

                }
            }

            // x is increasing  and y is decreasing
            else if (prevPoint != null && prevPoint.y >point.y   && prevPoint.x < point.x) {
                int newY = prevPoint.y;
                for (int i = prevPoint.x; i <= point.x; i += 10) {
                    inbound.add(new Point(i, newY));
                    newY -= 10;
                }
            }

            // x is decreasing  and y is decreasing
            if (prevPoint != null && prevPoint.y >point.y   && prevPoint.x > point.x) {
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
        return new TrackPath(inboundWhole);

//        return reverse(createPlatformBPath());
    }

    public static TrackPath createOutboundTrackFromPlatform(TrackPath platform) {
        // Reuse the last point of platform as the start of outbound
//        Point last = platform.getPoints().get(platform.getPoints().size() - 1);
//
//        List<Point> outbound = new ArrayList<>();
//        outbound.add(last); // start at end of platform
//        outbound.add(new Point(500, 200));
//        outbound.add(new Point(600, 200));
//        return new TrackPath(outbound);
        return createInboundTrackFromPlatform(platform);
    }
}
