package com.example.LiterAlura.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre;
    private Integer birthYear;
    private Integer deathYear;
    @OneToOne
    private Libro libro;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.birthYear = datosAutor.birthYear();
        this.deathYear = datosAutor.deathYear();
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", libro=" + libro +
                '}';
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }
}
