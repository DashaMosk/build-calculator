package com.dreamers.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"id", "facility"})
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int height;
    @ManyToOne
    @JoinColumn(name = "facilityId")
    private Facility facility;

    public Long getId() {
        return id;
    }

    public Room(String name, int height) {
        this.name = name;
        this.height = height;
    }
}
