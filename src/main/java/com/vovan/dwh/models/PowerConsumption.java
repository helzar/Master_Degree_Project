package com.vovan.dwh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Volodymyr Roman on 24.11.2016.
 */
public class PowerConsumption {
    private Integer id;
    private Integer transformerId;
    private String generationTimestamp;
    private Double losses;
    private Integer electricPower;
    private Boolean wasEnabled;
    private String insertionTimestamp;

    public PowerConsumption() {
    }

    public PowerConsumption(Integer id, Integer transformerId, String generationTimestamp, Double losses, Integer electricPower, Boolean wasEnabled, String insertionTimestamp) {
        this.id = id;
        this.transformerId = transformerId;
        this.generationTimestamp = generationTimestamp;
        this.losses = losses;
        this.electricPower = electricPower;
        this.wasEnabled = wasEnabled;
        this.insertionTimestamp = insertionTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                "id=" + id +
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
        return id + "," +
                transformerId + "," +
                generationTimestamp + "," +
                losses + "," +
                electricPower + "," +
                wasEnabled;
    }
}
