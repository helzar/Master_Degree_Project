package com.vovan.dwh.data_population;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vovan.dwh.data_population.events.TcpSocketDataPopulationEvent;
import com.vovan.dwh.data_population.events.TcpSocketDataPopulationEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr Roman on 25.11.2016.
 */
@Controller
@RequestMapping("/populate")
public class PopulationRestController {
    @Autowired
    private TcpSocketDataPopulationEventPublisher socketEventPublisher;

    @RequestMapping(value = "/mongodb", method = RequestMethod.POST)
    public ResponseEntity<String> mongoPopulation(
            @RequestParam(value = "startTime", defaultValue = "29-11-2016T11:00:00") String startTimestamp,
            @RequestParam(value = "startId", required = false) Integer startId,
            @RequestParam(value = "transformers", defaultValue = "1,2") String transformersString,
            @RequestParam(value = "numberOfMessages", defaultValue = "100") int numberOfMessages,
            @RequestParam(value = "dbName", defaultValue = "local") String dbName,
            @RequestParam(value = "collectionName", defaultValue = "energy_data") String collectionName,
            @RequestParam(value = "cleanMongoCollection", defaultValue = "true") boolean cleanMongoCollection
    ) throws JsonProcessingException {
        List<Integer> transformers = Arrays.stream(transformersString.split(",")).map(v -> Integer.parseInt(v)).collect(Collectors.toList());

        MongoPopulator populator = new MongoPopulator(dbName, collectionName);
        if (cleanMongoCollection) {
            populator.dropCollection();
        }

        OptionalInt optionalStartId = startId == null ? OptionalInt.empty() : OptionalInt.of(startId);
        populator.populate(startTimestamp, optionalStartId, transformers, numberOfMessages);

        return ResponseEntity.ok("messages are populated to MongoDB");
    }


    @RequestMapping(value = "/socket", method = RequestMethod.POST)
    public ResponseEntity<String> socketPopulation(
            @RequestParam(value = "startTime", defaultValue = "29-11-2016T11:00:00") String startTimestamp,
            @RequestParam(value = "startId", required = false) Integer startId,
            @RequestParam(value = "transformers", defaultValue = "1,2") String transformersString,
            @RequestParam(value = "numberOfMessages", defaultValue = "1") int numberOfMessages
    ) throws JsonProcessingException {
        List<Integer> transformers = Arrays.stream(transformersString.split(",")).map(v -> Integer.parseInt(v)).collect(Collectors.toList());

        OptionalInt optionalStartId = startId == null ? OptionalInt.empty() : OptionalInt.of(startId);
        TcpSocketDataPopulationEvent event = new TcpSocketDataPopulationEvent(startTimestamp, optionalStartId, transformers, numberOfMessages);
        socketEventPublisher.publish(event);

        return ResponseEntity.ok("Population request was published");
    }

}
