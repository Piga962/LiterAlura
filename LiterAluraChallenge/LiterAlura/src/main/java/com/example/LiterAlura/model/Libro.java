package com.example.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String autorNombre;
    @Transient
    private Autor autor;
    private String idiomas;
    private Integer numeroDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        //this.autor = datosLibro.autor();
        this.autorNombre = datosLibro.autor().get(0).nombre();
        this.idiomas = datosLibro.idiomas().get(0);
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor  getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", autorNombre=" + autorNombre +
                ", idiomas=" + idiomas +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
}
