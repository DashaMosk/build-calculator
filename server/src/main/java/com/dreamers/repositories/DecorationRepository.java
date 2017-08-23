package com.dreamers.repositories;

import com.dreamers.entities.Decoration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecorationRepository  extends CrudRepository<Decoration, Long> {

    @Query("select d from Decoration d where d.wall.id = :id")
    List<Decoration> findByWallId(@Param("id") Long id);
}
