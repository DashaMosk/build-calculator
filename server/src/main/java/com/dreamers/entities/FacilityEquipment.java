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
public class FacilityEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "stuffId")
    private Stuff stuff;
    private PartType type;
    private Long facilityID;
    @JsonProperty
    private FacilityType fType;

    public FacilityType getfType() {
        return fType;
    }

    public Stuff getStuff() {
        return stuff;
    }
}
