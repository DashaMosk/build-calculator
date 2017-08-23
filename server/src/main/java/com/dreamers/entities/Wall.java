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
    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    public Long getId() {
        return id;
    }

    public Wall(int height, int width) {
        this.height = height;
        this.width = width;
    }
}
