package com.dreamers.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"id", "room"})
public class Wall {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int height;
    private int width;
    private String name;
    private boolean forFloorCalculation;
    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public int getWidth() {
        return width;
    }

    public boolean isForFloorCalculation() {
        return forFloorCalculation;
    }

    public double getMeasurement() {
        return (double)(height*width)/10000;
    }
}
