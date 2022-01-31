package com.firstcommit.api.controller;

import com.firstcommit.api.dto.CandidateDto;
import com.firstcommit.api.dto.ImageDto;
import com.firstcommit.api.entities.Tag;
import com.firstcommit.api.security.payload.MessageResponse;
import com.firstcommit.api.services.candidate.CandidateServiceImpl;
import com.firstcommit.api.services.cloud.CloudinaryServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

/**
 * Controlador para el login de usuario
 * Si las credenciales son válidas se genera un token JWT como respuesta
 */
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateServiceImpl candidateServiceImpl;
    private final CloudinaryServiceImpl cloudinaryService;

    public CandidateController(CandidateServiceImpl candidateServiceImpl, CloudinaryServiceImpl cloudinaryService){
        this.candidateServiceImpl = candidateServiceImpl;
        this.cloudinaryService = cloudinaryService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    @ApiOperation("Get all users")
    public ResponseEntity<?> getAll(@CurrentSecurityContext(expression="authentication?.name") String owner){
        return candidateServiceImpl.getAll(owner);
    }

    /**
     * Método que devuelve todos los candidatos
     * @param owner
     * @return ResponseEntity con la lista de candidatos
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/filters")
    @ApiOperation("Get all users filtered")
    public ResponseEntity<?> getAllFilters(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean local,
            @RequestParam(required = false) Boolean remote,
            @RequestParam(required = false) Boolean transfer,
            @CurrentSecurityContext(expression="authentication?.name") String owner) {
        return candidateServiceImpl.getAllFilters(tags, country, city, local, remote, transfer, owner);
    }

    /**
     * Método que devuelve un candidato desde que el id coincida
     * @param id
     * @param owner
     * @return ResponseEntity con el candidato
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @ApiOperation("Get user by id")
    public ResponseEntity<?> getById(@PathVariable Long id, @CurrentSecurityContext(expression="authentication?.name") String owner){
        return candidateServiceImpl.getById(id, owner);
    }

    /**
     * Método para creación de un nuevo candidato
     * @param candidate
     * @param owner
     * @return ResponseEntity con el candidato
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    @ApiOperation("Create new user")
    public ResponseEntity<?> create(@RequestBody CandidateDto candidate, @CurrentSecurityContext(expression="authentication?.name") String owner){

        if (candidate.getFullname() == null ||
                candidate.getEmail() == null ||
                candidate.getPhone() == null ||
                candidate.getCountry() == null ||
                candidate.getCity() == null
        )
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Missing parameters"));

        return candidateServiceImpl.create(candidate, owner);
    }

    /**
     * Método para añadir foto de perfil
     * @param id
     * @param imageDto (multipart file)
     * @param owner
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/photo/{id}", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    @ApiOperation("Add a photo")
    public ResponseEntity<?> addPhoto(@PathVariable Long id, ImageDto imageDto, @CurrentSecurityContext(expression="authentication?.name") String owner){
        return candidateServiceImpl.addPhoto(id, imageDto, owner);
    }

    /**
     * Método para añadir pdf/curriculum
     * @param id
     * @param imageDto (multipart file)
     * @param owner
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/pdf/{id}")
    @ApiOperation("Add a pdf/curriculum")
    public ResponseEntity<?> addPdf(@PathVariable Long id, ImageDto imageDto, @CurrentSecurityContext(expression="authentication?.name") String owner){
        return candidateServiceImpl.addPdf(id, imageDto, owner);
    }

    /**
     * Método para actualización del candidato desde que el id coincida
     * @param id
     * @param candidate
     * @param owner
     * @return ResponseEntity con el candidato actualizado
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @ApiOperation("Update user by id")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CandidateDto candidate,
                                    @CurrentSecurityContext(expression="authentication?.name") String owner){
        if (candidate.getFullname() == null ||
                candidate.getEmail() == null ||
                candidate.getPhone() == null ||
                candidate.getCountry() == null ||
                candidate.getCity() == null
        )
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Missing parameters"));

        return candidateServiceImpl.update(id, candidate, owner);
    }

    /**
     * Método para remoción del candidato desde que el id coincida
     * @param id
     * @param owner
     * @return ResponseEntity
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ApiOperation("Remove user by id")
    public ResponseEntity<?> delete(@PathVariable Long id, @CurrentSecurityContext(expression="authentication?.name") String owner){
        return candidateServiceImpl.delete(id, owner);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/photo")
    @ApiOperation("Remove image by publicId")
    public Map deleteImage(@RequestBody String publicId){
        return cloudinaryService.deleteImage(publicId);
    }
}
