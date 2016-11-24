package com.vovan.dwh.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Volodymyr Roman on 24.11.2016.
 */
public class PowerConsumption {
    private Integer id;
    private Integer transformerId;
    private String timestamp;
    private Double losses;
    private Integer electricPower;
    private Boolean wasEnabled;

    public PowerConsumption() {}

    public PowerConsumption(Integer id, Integer transformerId, String timestamp, Double losses, Integer electricPower, Boolean wasEnabled) {
        this.id = id;
        this.transformerId = transformerId;
        this.timestamp = timestamp;
        this.losses = losses;
        this.electricPower = electricPower;
        this.wasEnabled = wasEnabled;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "PowerConsumption{" +
                "id=" + id +
                ", transformerId=" + transformerId +
                ", timestamp='" + timestamp + '\'' +
                ", losses=" + losses +
                ", electricPower=" + electricPower +
                ", wasEnabled=" + wasEnabled +
                '}';
    }
}
