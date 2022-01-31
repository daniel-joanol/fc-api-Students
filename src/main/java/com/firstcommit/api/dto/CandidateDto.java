package com.firstcommit.api.dto;

import com.firstcommit.api.entities.Tag;

import java.util.Set;

/**
 * Clase DTO del Candidato
 */
public class CandidateDto {

    private String fullname;
    private String email;
    private String phone;
    private boolean transfer;
    private boolean local;
    private boolean remote;
    private String country;
    private String city;
    private Set<Tag> tags;

    public CandidateDto(String fullname, String email, String phone, boolean transfer, boolean local, boolean remote,
                        String country, String city, Set<Tag> tags) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.transfer = transfer;
        this.local = local;
        this.remote = remote;
        this.country = country;
        this.city = city;
        this.tags = tags;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
