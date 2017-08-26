package com.dreamers.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ToString
@EqualsAndHashCode(exclude={"id"})
public class Measurement {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private final Long facilityId;
    private final Long roomId;
    private final Long wallId;
    private final Long apertureId;
    private final Long decorationId;
    private final double wallsM2;
    private final double ceilingM2;
    private final double floorM2;

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
