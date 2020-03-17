package com.example.pracownicy;

import com.example.pracownicy.domain.Pracownik;
import com.example.pracownicy.repository.PracownicyRepository;
import com.example.pracownicy.service.PracownicyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    final PracownicyService pracownicyService;

    public DataLoader(PracownicyService pracownicyService) {
        this.pracownicyService = pracownicyService;
    }

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Override
    public void run(String... args) throws Exception {
        pracownicyService.save(new Pracownik("Jan Kowalski", "500123063", 3200));
        pracownicyService.save(new Pracownik("Monika Pankowska", "500123065", 3300));
        pracownicyService.save(new Pracownik("Roman Witek", "500123061", 3200));
        pracownicyService.save(new Pracownik("Barbara Janda", "500123024", 3700));
        logger.info("Zakonczono dodawanie pracownikow.");
    }

}
