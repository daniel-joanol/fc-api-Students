package com.firstcommit.api.services.candidate;

import com.firstcommit.api.dto.CandidateDto;
import com.firstcommit.api.dto.ImageDto;
import com.firstcommit.api.entities.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Interfaz del servicio del candidato
 */
public interface CandidateService {

    ResponseEntity<?> getAll(String owner);
    ResponseEntity<?> getAllFilters(String tags, String country, String city, Boolean local, Boolean remote, Boolean transfer, String owner);
    ResponseEntity<?> getById(Long id, String owner);
    ResponseEntity<?> create(CandidateDto candidate, String owner);
    ResponseEntity<?> update(Long id, CandidateDto candidate, String owner);
    ResponseEntity<?> delete(Long id, String owner);
    ResponseEntity<?> addPhoto(Long id, ImageDto imageDto, String owner);
    ResponseEntity<?> addPdf(Long id, ImageDto imageDto, String owner);
    boolean validateTags(Set<Tag> tags);
    boolean validateCountryAndCity(String country, String city);
}
