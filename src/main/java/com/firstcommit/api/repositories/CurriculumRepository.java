package com.firstcommit.api.repositories;

import com.firstcommit.api.entities.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de la entidad City
 */
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    Optional<Curriculum> findByUrl(String url);
}
