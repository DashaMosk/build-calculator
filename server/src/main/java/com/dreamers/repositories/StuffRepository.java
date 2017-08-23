package com.dreamers.repositories;

import com.dreamers.entities.Stuff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuffRepository extends CrudRepository<Stuff, Long> {

    @Query("select s from Stuff s where s.isClean = :clean")
    List<Stuff> findByCleanParam(@Param("clean") boolean clean);
}
