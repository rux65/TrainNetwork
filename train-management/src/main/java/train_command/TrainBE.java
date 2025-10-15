package train_command;

import com.google.gson.Gson;

import java.util.List;

// producer
public class TrainBE {

        private String name;
        private String route;
        private int speed;
        private Direction direction;

        public enum Direction {
            LEFT, RIGHT
        }

    public TrainBE(String name, String route, int speed, Direction direction) {
        this.name = name;
        this.route = route;
        this.speed = speed;
        this.direction = direction;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static String toJsonList(List<TrainBE> trains) {
        TrainCommandMessage msg = new TrainCommandMessage(trains);
        return new Gson().toJson(msg);
    }

}

