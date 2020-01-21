package com.example.pracownicy;

import com.example.pracownicy.domain.Pracownik;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@SpringBootApplication
public class PracownicyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracownicyApplication.class, args);
	}

}
