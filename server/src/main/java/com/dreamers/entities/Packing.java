package com.dreamers.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"id"})
public class Packing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double quantity;
    private MeasureType unit;
    private double consumption;

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public MeasureType getUnit() {
        return unit;
    }

    public double getConsumption() {
        return consumption;
    }
}
