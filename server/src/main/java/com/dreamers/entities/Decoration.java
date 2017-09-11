package com.dreamers.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"id", "wall"})
public class Decoration {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String type;
    private int height;
    private int width;
    @ManyToOne
    @JoinColumn(name = "wallId")
    private Wall wall;

    public Long getId() {
        return id;
    }

    public Wall getWall() {
        return wall;
    }

    public double getMeasurement() {
        return (double)(height*width)/10000;
    }
}
