package train_command;

import java.util.List;

public class TrainCommandMessage {
    private List<TrainBE> trains;

    public TrainCommandMessage(List<TrainBE> trains) {
        this.trains = trains;
    }

    public List<TrainBE> getTrains() {
        return trains;
    }


}