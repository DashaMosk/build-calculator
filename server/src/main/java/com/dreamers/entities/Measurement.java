package com.dreamers.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(exclude={"id"})
public class Measurement {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long facilityId;
    private Long roomId;
    private Long wallId;
    private Long apertureId;
    private Long decorationId;
    private double wallsM2;
    private double ceilingM2;
    private double floorM2;

    public static class Builder {
        // Required parameters
        private Long facilityId;
        private Long roomId;
        // Optional parameters
        private Long wallId;
        private Long apertureId;
        private Long decorationId;
        private double wallsM2;
        private double ceilingM2;
        private double floorM2;

        public Builder(Long facilityId, Long roomId) {
            this.facilityId = facilityId;
            this.roomId = roomId;
        }

        public Builder wallId(Long val)
        { wallId = val; return this; }
        public Builder apertureId(Long val)
        { apertureId = val; return this; }
        public Builder decorationId(Long val)
        { decorationId = val; return this; }
        public Builder wallsM2(double val)
        { wallsM2 = val; return this; }
        public Builder ceilingM2(double val)
        { ceilingM2 = val; return this; }
        public Builder floorM2(double val)
        { floorM2 = val; return this; }

        public Measurement build() {
            return new Measurement(this);
        }
    }

    private Measurement() {};

    public Long getId() {
        return id;
    }

    public Long getApertureId() {
        return apertureId;
    }

    public Long getDecorationId() {
        return decorationId;
    }

    public Long getWallId() {
        return wallId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public double getWallsM2() {
        return wallsM2;
    }

    public double getCeilingM2() {
        return ceilingM2;
    }

    public double getFloorM2() {
        return floorM2;
    }

    private Measurement(Builder builder) {
        this.facilityId = builder.facilityId;
        this.roomId = builder.roomId;
        this.wallId = builder.wallId;
        this.apertureId = builder.apertureId;
        this.decorationId = builder.decorationId;
        this.wallsM2 = builder.wallsM2;
        this.ceilingM2 = builder.ceilingM2;
        this.floorM2 = builder.floorM2;
    }
}
