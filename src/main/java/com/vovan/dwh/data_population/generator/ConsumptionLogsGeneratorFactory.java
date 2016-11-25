package com.vovan.dwh.data_population.generator;

import com.vovan.dwh.models.PowerConsumption;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Created by Volodymyr Roman on 24.11.2016.
 */
public class ConsumptionLogsGeneratorFactory {

    /**
     * @param startTimestamp   starting logs generation from this value and adding PowerConsumptionLogsGenerator.LOGGING_INTERVAL interval for each new log
     * @param startId          each log will have unique id starting from startId value
     * @param transformers
     * @param numberOfMessages
     * @return iterator witch generates transformers.size * numberOfMessages logs
     */
    public static Iterator<PowerConsumption> create(String startTimestamp, OptionalInt startId,
                                                    List<Integer> transformers, int numberOfMessages) {

        return new PowerConsumptionLogsGenerator(new RandomMetricsGenerator(),
                startTimestamp, startId, transformers, numberOfMessages);
    }

}
