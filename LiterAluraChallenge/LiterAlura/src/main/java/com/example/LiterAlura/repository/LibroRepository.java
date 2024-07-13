package com.example.LiterAlura.repository;

import com.example.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdiomas(String idiomas);
    Optional<Libro> findByTitulo(String titulo);
}
