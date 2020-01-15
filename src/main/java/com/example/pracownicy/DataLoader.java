package com.example.pracownicy;

import com.example.pracownicy.domain.Pracownik;
import com.example.pracownicy.repository.PracownicyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    final PracownicyRepository pracownicyRepository;

    public DataLoader(PracownicyRepository pracownicyRepository) {
        this.pracownicyRepository = pracownicyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        pracownicyRepository.save(new Pracownik("Jan Kowalski", "500123063", 3200));
        pracownicyRepository.save(new Pracownik("Monika Pankowska", "500123065", 3300));
        pracownicyRepository.save(new Pracownik("Roman Witek", "500123061", 3200));
        pracownicyRepository.save(new Pracownik("Barbara Janda", "500123024", 3700));
    }

}
