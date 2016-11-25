package com.vovan.dwh.data_population.events;

import java.util.List;
import java.util.OptionalInt;

/**
 * Created by Volodymyr Roman on 25.11.2016.
 */
public class TcpSocketDataPopulationEvent {
    private String startTimestamp;
    private OptionalInt startId;
    private List<Integer> transformers;
    private int numberOfMessages;

    public TcpSocketDataPopulationEvent(String startTimestamp, OptionalInt startId, List<Integer> transformers, int numberOfMessages) {
        this.startTimestamp = startTimestamp;
        this.startId = startId;
        this.transformers = transformers;
        this.numberOfMessages = numberOfMessages;
    }

    public TcpSocketDataPopulationEvent() {}

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public OptionalInt getStartId() {
        return startId;
    }

    public void setStartId(OptionalInt startId) {
        this.startId = startId;
    }

    public List<Integer> getTransformers() {
        return transformers;
    }

    public void setTransformers(List<Integer> transformers) {
        this.transformers = transformers;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }
}
