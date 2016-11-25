package com.vovan.dwh.data_population.generator;

import com.vovan.dwh.models.PowerConsumption;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Created by Volodymyr Roman on 24.11.2016.
 */
public class PowerConsumptionLogsGenerator implements Iterator<PowerConsumption> {
    private static Duration LOGGING_INTERVAL = Duration.ofHours(1);
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");

    private final RandomMetricsGenerator randomMetricsGenerator;
    private final String startTimestamp;
    private final List<Integer> transformers;
    private final int numberOfMessages;

    private int currentTransformer = 0;
    private int generatedLog = 0;
    private LocalDateTime currentTimestamp = null;
    private OptionalInt currentId;


    public PowerConsumptionLogsGenerator(RandomMetricsGenerator randomMetricsGenerator, String startTimestamp,
                                         OptionalInt startId, List<Integer> transformers, int numberOfMessages) {
        this.randomMetricsGenerator = randomMetricsGenerator;
        this.startTimestamp = startTimestamp;
        this.currentId = startId;
        this.transformers = transformers;
        this.numberOfMessages = numberOfMessages;
    }

    @Override
    public boolean hasNext() {
        if (currentTransformer >= transformers.size()) {
            return false;
        } else if (currentTransformer == transformers.size() - 1 && generatedLog >= numberOfMessages) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public PowerConsumption next() {
        if (generatedLog >= numberOfMessages) {
            generatedLog = 0;
            currentTransformer++;
        }

        if (generatedLog == 0) {
            currentTimestamp = LocalDateTime.parse(startTimestamp, DATE_FORMATTER);
        } else {
            currentTimestamp = currentTimestamp.plusSeconds(LOGGING_INTERVAL.getSeconds());
        }

        generatedLog++;
        int electricPower = randomMetricsGenerator.generateElectricPower();
        Integer logId = getLogId();
        return new PowerConsumption(
                logId,
                transformers.get(currentTransformer),
                currentTimestamp.toString(),
                randomMetricsGenerator.generateLossesBaseWithCorrelationToElectricPower(electricPower),
                electricPower,
                randomMetricsGenerator.generateWasEnabledParameter()
        );
    }

    public Integer getLogId() {
        if (currentId.isPresent()){
            Integer logId = currentId.getAsInt();
            currentId = OptionalInt.of(currentId.getAsInt() + 1);
            return logId;
        } else {
            return null;
        }
    }
}