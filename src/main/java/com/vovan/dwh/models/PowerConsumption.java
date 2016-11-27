package com.vovan.dwh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Volodymyr Roman on 24.11.2016.
 */
public class PowerConsumption {
    private Integer log_id;
    private Integer transformerId;
    private String generationTimestamp;
    private Double losses;
    private Integer electricPower;
    private Boolean wasEnabled;
    // TODO: currently is used for MySQL and MongoDB. For MongoDB its not applicable approach as insertionTimestamp is set on client, not server
    // To fix this ETL from MongoDB to cluster should be done via Spark Streaming using Kafka and MongoDB Kafka build in streaming (TODO: investigate its exactly one delivery)
    private String insertionTimestamp;

    public PowerConsumption() {
    }

    public PowerConsumption(Integer log_id, Integer transformerId, String generationTimestamp, Double losses, Integer electricPower, Boolean wasEnabled, String insertionTimestamp) {
        this.log_id = log_id;
        this.transformerId = transformerId;
        this.generationTimestamp = generationTimestamp;
        this.losses = losses;
        this.electricPower = electricPower;
        this.wasEnabled = wasEnabled;
        this.insertionTimestamp = insertionTimestamp;
    }

    public Integer getLog_id() {
        return log_id;
    }

    public void setLog_id(Integer log_id) {
        this.log_id = log_id;
    }

    @JsonProperty("transformer_id")
    public Integer getTransformerId() {
        return transformerId;
    }

    public void setTransformerId(Integer transformerId) {
        this.transformerId = transformerId;
    }

    @JsonProperty("generation_timestamp")
    public String getGenerationTimestamp() {
        return generationTimestamp;
    }

    public void setGenerationTimestamp(String generationTimestamp) {
        this.generationTimestamp = generationTimestamp;
    }

    public Double getLosses() {
        return losses;
    }

    public void setLosses(Double losses) {
        this.losses = losses;
    }

    @JsonProperty("electric_power")
    public Integer getElectricPower() {
        return electricPower;
    }

    public void setElectricPower(Integer electricPower) {
        this.electricPower = electricPower;
    }

    @JsonProperty("was_enabled")
    public boolean isWasEnabled() {
        return wasEnabled;
    }

    public void setWasEnabled(boolean wasEnabled) {
        this.wasEnabled = wasEnabled;
    }

    @JsonProperty("insertion_timestamp")
    public String getInsertionTimestamp() {
        return insertionTimestamp;
    }

    public void setInsertionTimestamp(String insertionTimestamp) {
        this.insertionTimestamp = insertionTimestamp;
    }

    @Override
    public String toString() {
        return "PowerConsumption{" +
                "log_id=" + log_id +
                ", transformerId=" + transformerId +
                ", generationTimestamp='" + generationTimestamp + '\'' +
                ", losses=" + losses +
                ", electricPower=" + electricPower +
                ", wasEnabled=" + wasEnabled +
                ", insertionTimestamp='" + insertionTimestamp + '\'' +
                '}';
    }

    /**
     * Method builds csv format line to be streamed via TCP Socket
     * insertion_timestamp field is not included in output line
     * @return csv format line
     */
    public String toLineFormat() {
        return log_id + "," +
                transformerId + "," +
                generationTimestamp + "," +
                losses + "," +
                electricPower + "," +
                wasEnabled;
    }
}
