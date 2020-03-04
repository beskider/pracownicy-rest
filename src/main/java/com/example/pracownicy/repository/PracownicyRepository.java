package com.example.pracownicy.repository;

import com.example.pracownicy.domain.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PracownicyRepository extends JpaRepository<Pracownik, Long> {
    List<Pracownik> findByNazwa(String nazwa);
}
