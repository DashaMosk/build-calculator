package com.dreamers.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude={"id"})
public class CalculationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final double m2;
    @JsonProperty
    private final boolean isClean;
    private final String stuffName;
    private final double consumption;
    private final String measureName;
    private final double packConsumption;
    private final String packName;

    @ManyToOne
    @JoinColumn(name = "measurementId")
    private final Measurement measurement;

    public static class Builder {
        private double m2;
        private boolean isClean;
        private String stuffName;
        private double consumption;
        private String measureName;
        private double packConsumption;
        private String packName;
        private Measurement measurement;

        public Builder(Measurement measurement, double m2, boolean isClean) {
            this.measurement = measurement;
            this.m2 = m2;
            this.isClean = isClean;
        }
        public Builder stuffName(String stuffName) {
            this.stuffName = stuffName; return this;
        }
        public Builder consumption(double consumption) {
            this.consumption = consumption; return this;
        }
        public Builder measureName(String measureName) {
            this.measureName = measureName; return this;
        }
        public Builder packConsumption(double packConsumption) {
            this.packConsumption = packConsumption; return this;
        }
        public Builder packName(String packName) {
            this.packName = packName; return this;
        }

        public CalculationResult build() {
            return new CalculationResult(this);
        }
    }

    public double getM2() {
        return m2;
    }

    public boolean isClean() {
        return isClean;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    private CalculationResult(Builder builder) {
        this.m2 = builder.m2;
        this.isClean = builder.isClean;
        this.stuffName = builder.stuffName;
        this.consumption = builder.consumption;
        this.measureName = builder.measureName;
        this.packConsumption = builder.packConsumption;
        this.packName = builder.packName;
        this.measurement = builder.measurement;
    }

}
