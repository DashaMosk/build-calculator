package com.dreamers.repositories;

import com.dreamers.entities.Aperture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApertureRepository  extends CrudRepository<Aperture, Long> {

    @Query("select a from Aperture a where a.wall.id = :id")
    List<Aperture> findByWallId(@Param("id") Long id);

    @Query("select a.type from Aperture a where a.id = :id")
    String getNameById(@Param("id") Long id);
}
