package com.firstcommit.api.repositories;

import com.firstcommit.api.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de la entidad Candidate
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Boolean existsByFullname(String fullname);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}
