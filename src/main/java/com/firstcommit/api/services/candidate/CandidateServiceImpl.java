package com.firstcommit.api.services.candidate;

import com.firstcommit.api.dto.CandidateDto;
import com.firstcommit.api.dto.ImageDto;
import com.firstcommit.api.dto.ResponseCandidateDto;
import com.firstcommit.api.entities.*;
import com.firstcommit.api.repositories.*;
import com.firstcommit.api.security.payload.MessageResponse;
import com.firstcommit.api.services.cloud.CloudinaryServiceImpl;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Implementación de la interfaz CandidateService.
 */
@Service
public class CandidateServiceImpl {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final CloudinaryServiceImpl cloudinary;
    private final TagRepository tagRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository, UserRepository userRepository,
                                CloudinaryServiceImpl cloudinary, TagRepository tagRepository,
                                CountryRepository countryRepository, CityRepository cityRepository){
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
        this.tagRepository = tagRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * Método que devuelve todos los candidatos
     * @param owner
     * @return ResponseEntity con la lista de candidatos
     */
    public ResponseEntity<?> getAll(String owner){
        List<Candidate> allCandidates = candidateRepository.findAll();
        List<ResponseCandidateDto> resultDto = new ArrayList<>();
        Optional<User> optUser = userRepository.findByUsername(owner);

        for (Candidate candidate : allCandidates) {
            if (candidate.getUser() == optUser.get())
                resultDto.add(candidate.getResponseDto());
        }

        if (resultDto.size() == 0)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(resultDto);
    }

    /**
     * Método que devuelve todos los candidatos, según los filtros
     * @param owner
     * @return ResponseEntity con la lista de candidatos
     */
    public ResponseEntity<?> getAllFilters(String tags, String country, String city, Boolean local, Boolean remote, Boolean transfer, String owner){

        //Gets the information of the user who is requesting candidates
        List<ResponseCandidateDto> resultDto = new ArrayList<>();
        Optional<User> optUser = userRepository.findByUsername(owner);

        Country finalCountry = null;
        City finalCity = null;
        Set<Tag> finalTags = new HashSet();
        String[] arrayTags;

        if (country != null){
            Optional<Country> optCountry = countryRepository.findByName(country);
            if (optCountry.isPresent())
                finalCountry = optCountry.get();
            else
                return ResponseEntity.noContent().build();
        }

        if(city != null){
            Optional<City> optCity = cityRepository.findByName(city);
            if (optCity.isPresent())
                finalCity = optCity.get();
            else
                return ResponseEntity.noContent().build();
        }

        if(tags != null){
            //Convert the String tags into an Array, then into a Set<Tag>
            arrayTags = tags.split(",");

            for (String tag : arrayTags)
                finalTags.add(new Tag(tag));
        } else {
            finalTags = new HashSet<>();
        }

        if (!validateTags(finalTags))
            return ResponseEntity.noContent().build();

        for (Tag tag : finalTags)
            System.out.println(tag.getName());

        ExampleMatcher example = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Candidate> query = Example.of(new Candidate(null, null, null, transfer, local, remote,
                finalCountry, finalCity, finalTags, optUser.get()), example);
        List<Candidate> allCandidates = candidateRepository.findAll(query);

        for (Candidate candidate : allCandidates){

            //Compare the tags, cause the query gets candidates with any set of tags
            if (finalTags != null){
                int equalTags = 0;
                for (Tag tag : finalTags){
                    if (candidate.getTags() != null) {
                        for (Tag tag2 : candidate.getTags()){
                            if (tag.getName().equalsIgnoreCase(tag2.getName()))
                                equalTags++;
                        }
                    }
                }

                if (finalTags.size() == equalTags){
                    resultDto.add(candidate.getResponseDto());
                }
            } else {
                resultDto.add(candidate.getResponseDto());
            }
        }

        if (resultDto.size() == 0)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(resultDto);
    }

    /**
     * Método que devuelve un candidato desde que el id coincida
     * @param id
     * @param owner
     * @return ResponseEntity con ResponseCandidateDto
     */
    public ResponseEntity<?> getById(Long id, String owner){
        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        Optional<User> optUser = userRepository.findByUsername(owner);

        if (optCandidate.isPresent() && optCandidate.get().getUser() == optUser.get())
            return ResponseEntity.ok(optCandidate.get().getResponseDto());

        return ResponseEntity.notFound().build();
    }

    /**
     * Método para creación de un nuevo candidato
     * @param candidate
     * @param owner
     * @return ResponseEntity con el ResponseCandidateDto
     */
    public ResponseEntity<?> create(CandidateDto candidate, String owner) {

        Optional<Country> optCountry;
        Optional<City> optCity;

        //Verifica si el username ya esta registrado
        if (candidateRepository.existsByFullname(candidate.getFullname()))
            return ResponseEntity.badRequest().body(new MessageResponse("Name already in use"));

        //Verifica si el email ya esta registrado
        if (candidateRepository.existsByEmail(candidate.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse("Email already in use"));

        //Verifica si el telefono ya esta registrado
        if (candidateRepository.existsByPhone(candidate.getPhone()))
            return ResponseEntity.badRequest().body(new MessageResponse("Phone already in use"));

        //Verifica si las tags son validas
        if (!validateTags(candidate.getTags()))
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid tags. The allowed tags are: 'Java', 'Spring', 'J-Unit'," +
                    " 'Hibernate', 'Git', 'CSS-HTML', 'JavaScript', 'React' and 'Angular'"));

        //Verifica si pais y ciudad son validos. Tambien verifica si la ciudad pertenece al pais
        if (!validateCountryAndCity(candidate.getCountry(), candidate.getCity()))
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid country and/or city"));

        optCountry = countryRepository.findByName(candidate.getCountry());
        optCity = cityRepository.findByName(candidate.getCity());
        Optional<User> optUser = userRepository.findByUsername(owner);


        Candidate result = new Candidate(candidate.getFullname(), candidate.getEmail(), candidate.getPhone(),
                    candidate.isTransfer(), candidate.isLocal(), candidate.isRemote(), optCountry.get(),
                    optCity.get(), candidate.getTags(), optUser.get());

        candidateRepository.save(result);

        return ResponseEntity.ok(result.getResponseDto());
    }

    /**
     * Método para actualización del candidato desde que el id coincida
     * @param id
     * @param candidate
     * @param owner
     * @return ResponseEntity con el ResponseCandidateDto del candidato actualizado
     */
    public ResponseEntity<?> update(Long id, CandidateDto candidate, String owner) {

        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        Optional<User> optUser = userRepository.findByUsername(owner);
        Optional<Country> optCountry;
        Optional<City> optCity;

        //Verifica si las tags son validas
        if (!validateTags(optCandidate.get().getTags()))
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid tags. The allowed tags are: 'Java', 'Spring', 'J-Unit'," +
                    " 'Hibernate', 'Git', 'CSS-HTML', 'JavaScript', 'React' and 'Angular'"));

        //Verifica si pais y ciudad son validos. Tambien verifica si la ciudad pertenece al pais
        if (!validateCountryAndCity(candidate.getCountry(), candidate.getCity()))
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid country and/or city"));

        optCountry = countryRepository.findByName(candidate.getCountry());
        optCity = cityRepository.findByName(candidate.getCity());

        if (optCandidate.isPresent() &&
                optCandidate.get().getUser() == optUser.get())
        {
            optCandidate.get().setFullname(candidate.getFullname());
            optCandidate.get().setEmail(candidate.getEmail());
            optCandidate.get().setPhone(candidate.getPhone());
            optCandidate.get().setLocal(candidate.isLocal());
            optCandidate.get().setTransfer(candidate.isTransfer());
            optCandidate.get().setRemote(candidate.isRemote());
            optCandidate.get().setCountry(optCountry.get());
            optCandidate.get().setCity(optCity.get());
            optCandidate.get().setTags(candidate.getTags());

            candidateRepository.save(optCandidate.get());
            return ResponseEntity.ok(optCandidate.get().getResponseDto());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Método para remoción del candidato desde que el id coincida
     * @param id
     * @param owner
     * @return ResponseEntity
     */
    public ResponseEntity<?> delete(Long id, String owner) {
        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        Optional<User> optUser = userRepository.findByUsername(owner);

        if(optCandidate.isPresent() && optCandidate.get().getUser() == optUser.get()){
            candidateRepository.delete(optCandidate.get());
            return ResponseEntity.ok(new MessageResponse("Candidate " + id + " removed"));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Método para upload de la photo
     * @param id
     * @param imageDto (multpart file)
     * @param owner
     * @return url de la photo en el servidor
     */
    public ResponseEntity<?> addPhoto(Long id, ImageDto imageDto, String owner) {
        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        Optional<User> optUser = userRepository.findByUsername(owner);

        if(optCandidate.isPresent() && optCandidate.get().getUser() == optUser.get()){
            try{

                Photo photo = new Photo(cloudinary.uploadImage(imageDto.getImage()));
                optCandidate.get().setPhoto(photo);
                candidateRepository.save(optCandidate.get());

                return ResponseEntity.ok().body(optCandidate.get().getPhoto());

            } catch (Exception e){
                return ResponseEntity
                        .badRequest()
                        .body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Método para upload del curriculum
     * @param id
     * @param imageDto (multpart file)
     * @param owner
     * @return url de la photo en el servidor
     */
    public ResponseEntity<?> addPdf(Long id, ImageDto imageDto, String owner) {
        Optional<Candidate> optCandidate = candidateRepository.findById(id);
        Optional<User> optUser = userRepository.findByUsername(owner);

        if(optCandidate.isPresent() && optCandidate.get().getUser() == optUser.get()){
            try{
                Curriculum curriculum = new Curriculum(cloudinary.uploadImage(imageDto.getImage()));
                optCandidate.get().setCurriculum(curriculum);
                candidateRepository.save(optCandidate.get());

                return ResponseEntity.ok().body(optCandidate.get().getCurriculum());

            } catch (Exception e){
                return ResponseEntity
                        .badRequest()
                        .body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Verifica si el Set<Tag> contiene apenas tags válidas
     * @param tags
     * @return boolean
     */
    public boolean validateTags(Set<Tag> tags){
        //Tuve que cambiar Optional por List pues cuando utilizaba Optional y buscaba por un String 'Java'
        // daba conflicto con String 'JavaScript'.
        List<Tag> tagsFromBd;
        int i;

        for (Tag tag : tags){
            tagsFromBd = tagRepository.findByName(tag.getName());
            if (tagsFromBd.size() == 0)
                return false;
        }

        return true;
    }

    /**
     * Verifica si los Strings country y city son validos y si la ciudad pertenece al país
     * @param country String
     * @param city String
     * @return boolean
     */
    boolean validateCountryAndCity(String country, String city){

        Optional<Country> optCountry = countryRepository.findByName(country);
        Set<City> optCities;
        int control;

        if (optCountry.isPresent()){
            optCities = optCountry.get().getCities();
            control = optCities.size();

            for (City cityFromBd : optCities){

                if (cityFromBd.getName().equalsIgnoreCase(city))
                    control--;
            }

            if (control != optCities.size())
                return true;
        }

        return false;
    }

    public ResponseEntity<?> addPhoto2(MultipartFile file) {
        try{

            String url = cloudinary.uploadImage(file);

            return ResponseEntity.ok().body(url);

        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
