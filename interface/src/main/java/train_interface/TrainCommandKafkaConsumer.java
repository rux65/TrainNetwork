package train_interface;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TrainCommandKafkaConsumer {

    private final TrainPanel panel;
    private final List<TrackPath> trackPaths;
    private final  Map<String, TrackPath> routeMap;

    public TrainCommandKafkaConsumer(TrainPanel panel, List<TrackPath> trackPaths, Map<String, TrackPath> routeMap) {
        this.panel = panel;
        this.trackPaths = trackPaths;
        this.routeMap = routeMap;
    }

    public void startListening() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "train-ui-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("train-control"));

        new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    System.out.println("records in command receiver "+ records.count());
                    for (ConsumerRecord<String, String> record : records) {
                        handleMessage(record.value());
                    }
                }
            } finally {
                consumer.close();
            }
        }).start();
    }

    private void handleMessage(String jsonMessage) {
        // Deserialize JSON
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(jsonMessage, JsonObject.class);
        JsonArray trainsArray = obj.getAsJsonArray("trains");
        System.out.println("=========="+ trainsArray);

        for (JsonElement elem : trainsArray) {
            JsonObject trainObj = elem.getAsJsonObject();

            String name = trainObj.get("name").getAsString();
            String directionStr = trainObj.get("direction").getAsString();
            int speed = trainObj.get("speed").getAsInt();

            Direction direction = Direction.valueOf(directionStr.toUpperCase());

            String routeName = trainObj.get("route").getAsString();
            TrackPath path = routeMap.get(routeName);
            path = direction == Direction.RIGHT ?  TrackPath.reverse(path): path;

            if (path == null) {
                System.err.println(" Unknown route: " + routeName);
                return;
            }

            Train train = new Train(name, path, speed, direction);
            panel.addTrain(train); // add to panel if needed
            new Thread(train).start();
        }
    }
}