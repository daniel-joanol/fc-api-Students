package com.firstcommit.api.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Entidad que gestiona los países en la base de datos.
 */
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "COUNTRIES_CITIES",
            joinColumns = {
                    @JoinColumn(name = "COUNTRY_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "CITY_ID") })
    private Set<City> cities;

    public Country() {
    }

    public Country(Long id, String name, Set<City> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }
}
