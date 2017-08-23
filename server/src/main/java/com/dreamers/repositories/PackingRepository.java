package com.dreamers.repositories;

import com.dreamers.entities.Packing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackingRepository extends CrudRepository<Packing, Long> {
}
