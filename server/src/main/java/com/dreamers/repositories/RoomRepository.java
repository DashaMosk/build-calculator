package com.dreamers.repositories;

import com.dreamers.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository  extends CrudRepository<Room, Long> {

    @Query("select r from Room r where r.facility.id = :id")
    List<Room> findByFacilityId(@Param("id") Long id);
}
