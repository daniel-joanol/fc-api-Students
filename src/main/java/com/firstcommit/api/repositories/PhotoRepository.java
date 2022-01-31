package com.firstcommit.api.repositories;

import com.firstcommit.api.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de la entidad Photo
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByUrl(String url);
}
