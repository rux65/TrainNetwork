package train_command;

import org.slf4j.LoggerFactory;
import train_interface.TrainRun;

/**
 * Hello world!Direction
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("SLF4J Logger: " + LoggerFactory.getILoggerFactory().getClass());

        // Start the consumer
        String trainPositionTopic = "train-positions";
        TrainMonitorConsumer.startConsumer(trainPositionTopic);

        TrainRun.runTrains();
        Thread.sleep(3000);
        TrainCommandProducer commandProducer = new TrainCommandProducer();
        commandProducer.sendTrainCommands();

    }


}
