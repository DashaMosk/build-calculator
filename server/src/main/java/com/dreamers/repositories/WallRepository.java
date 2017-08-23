package com.dreamers.repositories;

import com.dreamers.entities.Wall;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WallRepository  extends CrudRepository<Wall, Long> {

    @Query("select w from Wall w where w.room.id = :id")
    List<Wall> findByRoomId(@Param("id") Long id);
}
