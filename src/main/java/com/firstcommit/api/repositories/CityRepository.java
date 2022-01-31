package com.firstcommit.api.repositories;

import com.firstcommit.api.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de la entidad City
 */
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);
}
