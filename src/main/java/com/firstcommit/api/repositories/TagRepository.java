package com.firstcommit.api.repositories;

import com.firstcommit.api.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de la entidad Tag
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    //Tuve que cambiar Optional por List pues cuando utilizaba Optional y buscaba por Java
    // daba conflicto con JavaScript.
    List<Tag> findByName(String name);
}
