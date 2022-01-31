package com.firstcommit.api.entities;

import com.firstcommit.api.dto.ResponseCandidateDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que gestiona los candidatos en la base de datos
 */
@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullname;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private Boolean transfer;

    @Column
    private Boolean local;

    @Column
    private Boolean remote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "CANDIDATE_COUNTRY",
            joinColumns = {
                    @JoinColumn(name = "CANDIDATE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "COUNTRY_ID") })
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "CANDIDATE_CITY",
            joinColumns = {
                    @JoinColumn(name = "CANDIDATE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "CITY_ID") })
    private City city;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "CANDIDATE_PHOTO",
            joinColumns = {
                    @JoinColumn(name = "CANDIDATE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "PHOTO_ID") })
    private Photo photo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "CANDIDATE_CURRICULUM",
            joinColumns = {
                    @JoinColumn(name = "CANDIDATE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "CURRICULUM_ID") })
    private Curriculum curriculum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "CANDIDATE_TAGS",
            joinColumns = {
                    @JoinColumn(name = "CANDIDATE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "TAG_ID") })
    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Candidate() {
    }

    public Candidate(String fullname, String email, String phone, Boolean transfer, Boolean local,
                     Boolean remote, Country country, City city, Set<Tag> tags, User user) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.transfer = transfer;
        this.local = local;
        this.remote = remote;
        this.country = country;
        this.city = city;
        this.tags = tags;
        this.user = user;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public Boolean isLocal() {
        return local;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }

    public Boolean isRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //Other functions
    public ResponseCandidateDto getResponseDto(){
        Set<String> newTags = new HashSet<>();
        for (Tag tag : tags)
            newTags.add(tag.getName());

        String strPhotoUrl = "null", strCurriculumUrl = "null";

        if (photo != null)
            strPhotoUrl = photo.getUrl();

        if (curriculum != null)
            strCurriculumUrl = curriculum.getUrl();

        return new ResponseCandidateDto(id, fullname, email, phone, transfer, local, remote,
                country.getName(), city.getName(), newTags, user.getUsername(), strPhotoUrl, strCurriculumUrl);
    }
}
