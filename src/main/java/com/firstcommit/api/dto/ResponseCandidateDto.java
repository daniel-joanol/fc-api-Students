package com.firstcommit.api.dto;

import java.util.Set;

/**
 * Dto con los datos del candidato para respuestas
 */
public class ResponseCandidateDto {

    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private boolean transfer;
    private boolean local;
    private boolean remote;
    private String country;
    private String city;
    private Set<String> tags;
    private String owner;
    private String photoUrl;
    private String curriculumUrl;

    public ResponseCandidateDto() {
    }

    public ResponseCandidateDto(Long id, String fullname, String email, String phone, boolean transfer, boolean local,
                                boolean remote, String country, String city, Set<String> tags, String owner,
                                String photoUrl, String curriculumUrl) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.transfer = transfer;
        this.local = local;
        this.remote = remote;
        this.country = country;
        this.city = city;
        this.tags = tags;
        this.owner = owner;
        this.photoUrl = photoUrl;
        this.curriculumUrl = curriculumUrl;
    }

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

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCurriculumUrl() {
        return curriculumUrl;
    }

    public void setCurriculumUrl(String curriculumUrl) {
        this.curriculumUrl = curriculumUrl;
    }

}
