package com.vovan.dwh.data_population;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.vovan.dwh.data_population.generator.ConsumptionLogsGeneratorFactory;
import com.vovan.dwh.models.PowerConsumption;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;

/**
 * Created by Volodymyr Roman on 25.11.2016.
 */
public class MongoPopulator {
    private static final int BATCH_SIZE = 100;
    private static MongoClient mongoClient = new MongoClient();

    private final MongoCollection collection;
    private final ObjectMapper mapper = new ObjectMapper();


    public MongoPopulator(String dbName, String collectionName) {
        collection = mongoClient.getDatabase(dbName).getCollection(collectionName);
    }

    /**
     * Just for manual testing
     */
    public static void main(String[] args) throws JsonProcessingException {
        MongoPopulator populator = new MongoPopulator("local", "energy_data");
        populator.dropCollection();
        populator.populate("29-11-2016T11:00:00", OptionalInt.of(1), Arrays.asList(1, 2), 200);
    }

    public void populate(String startTimestamp, OptionalInt startId, List<Integer> transformers, int numberOfMessages) throws JsonProcessingException {
        Iterator<PowerConsumption> logsIter =
                ConsumptionLogsGeneratorFactory.create(startTimestamp, startId, transformers, numberOfMessages, true);
        List<Document> buffer = new ArrayList<>(BATCH_SIZE);

        while (logsIter.hasNext()) {
            PowerConsumption log = logsIter.next();
            Document bsonLog = Document.parse(mapper.writeValueAsString(log));
            buffer.add(bsonLog);
            if (buffer.size() >= BATCH_SIZE) {
                collection.insertMany(buffer);
                buffer.clear();
            }
        }
        if (buffer.size() > 0) {
            collection.insertMany(buffer);
            buffer.clear();
        }
    }

    public void dropCollection() {
        collection.drop();
    }
}
