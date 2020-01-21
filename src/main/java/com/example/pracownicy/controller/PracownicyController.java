package com.example.pracownicy.controller;

import com.example.pracownicy.domain.Pracownik;
import com.example.pracownicy.exception.PracownikIdNieprawidlowyException;
import com.example.pracownicy.exception.PracownikNieZnalezionyException;
import com.example.pracownicy.repository.PracownicyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pracownicy")
public class PracownicyController {

    private final PracownicyRepository pracownicyRepository;

    public PracownicyController(PracownicyRepository pracownicyRepository) {
        this.pracownicyRepository = pracownicyRepository;
    }

    @GetMapping
    public Iterable findAll() {
        return pracownicyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pracownik findById(@PathVariable Long id) {
        return pracownicyRepository.findById(id).orElseThrow(PracownikNieZnalezionyException::new);
    }

    @GetMapping({"/nazwa/{nazwa}"})
    public List findByNazwa(@PathVariable String nazwa) {
        return pracownicyRepository.findByNazwa(nazwa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pracownik create(@RequestBody Pracownik pracownik) {
        return pracownicyRepository.save(pracownik);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pracownicyRepository.findById(id).orElseThrow(PracownikNieZnalezionyException::new);
        pracownicyRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Pracownik update(@RequestBody Pracownik pracownik, @PathVariable Long id) {
        if (pracownik.getId() != id) {
            throw new PracownikIdNieprawidlowyException();
        }
        pracownicyRepository.findById(id).orElseThrow(PracownikNieZnalezionyException::new);
        return pracownicyRepository.save(pracownik);
    }

}
