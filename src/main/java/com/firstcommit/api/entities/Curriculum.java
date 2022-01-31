package com.firstcommit.api.entities;

import javax.persistence.*;

/**
 * Entidad que gestiona los curriculums en la base de datos
 */
@Entity
@Table(name = "curriculums")
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    public Curriculum() {
    }

    public Curriculum(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
