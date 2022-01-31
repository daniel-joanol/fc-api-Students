package com.firstcommit.api.repositories;

import com.firstcommit.api.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de la entidad Country
 */
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByName(String name);
}
