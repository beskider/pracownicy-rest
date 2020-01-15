package com.example.pracownicy.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Pracownik {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nazwa;

    private String numerKontaktowy;

    private Double pensja;

    public Pracownik() {
    }

    public Pracownik(String nazwa, String numerKontaktowy, double pensja) {
        this.nazwa = nazwa;
        this.numerKontaktowy = numerKontaktowy;
        this.pensja = pensja;
    }

}
