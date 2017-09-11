package com.dreamers.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"id"})
public class Stuff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @JoinColumn(name = "packingId")
    @ManyToOne
    private Packing packing;
    private double consumption;
    @JsonProperty
    private boolean isClean;
    private double price;

    public Long getId() {
        return id;
    }

    public boolean isClean() {
        return isClean;
    }

    public String getName() {
        return name;
    }

    public Packing getPacking() {
        return packing;
    }

    public double getConsumption() {
        return consumption;
    }

    public double getPrice() {
        return price;
    }
}
