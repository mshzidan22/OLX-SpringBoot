package com.olx.repository;


import com.olx.model.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    public Optional<Location> findByNameIgnoreCase (String name);

}
