package com.example.LiterAlura.repository;

import com.example.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.birthYear <= :year AND a.deathYear >= :year")
    List<Autor> autoresVivos(Integer year);
}
