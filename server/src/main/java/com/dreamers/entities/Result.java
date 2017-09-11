package com.dreamers.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {
    private Long facilityId;
    private Long roomId;
    private Long stuffId;
    private String stuffName;
    @JsonProperty
    private boolean isClean;
    private double consumption;
    private String measureName;
    private double packConsumption;
    private String packName;
    private double m2;
    private double calcConsumption;
    private double calcPackConsumption;

    public Result(Long facilityId, Long roomId, Long
            stuffId, String stuffName, boolean isClean,
                  double consumption, String measureName, double packConsumption, String packName, double m2) {
        this.facilityId = facilityId;
        this.roomId = roomId;
        this.stuffId = stuffId;
        this.stuffName = stuffName;
        this.isClean = isClean;
        this.consumption = consumption;
        this.measureName = measureName;
        this.packConsumption = packConsumption;
        this.packName = packName;
        this.m2 = m2;
        this.calcConsumption = m2 * consumption;
        this.calcPackConsumption = m2 * consumption/packConsumption;
    }
}
