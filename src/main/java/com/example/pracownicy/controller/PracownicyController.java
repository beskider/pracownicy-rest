package com.example.pracownicy.controller;

import com.example.pracownicy.domain.Pracownik;
import com.example.pracownicy.exception.PracownikIdNieprawidlowyException;
import com.example.pracownicy.exception.PracownikNieZnalezionyException;
import com.example.pracownicy.repository.PracownicyRepository;
import com.example.pracownicy.service.PracownicyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/pracownicy")
public class PracownicyController {

    private final PracownicyService pracownicyService;
    private final ServletContext context;

    private static final Logger logger = LoggerFactory.getLogger(PracownicyController.class);

    @Autowired
    public PracownicyController(PracownicyService pracownicyService, ServletContext context) {
        this.pracownicyService = pracownicyService;
        this.context = context;
    }

    @GetMapping
    public Iterable findAll() {
        logger.debug("Wywolano /api/pracownicy metoda HTTP GET");
        return pracownicyService.getAll();
    }

    @GetMapping("/{id}")
    public Pracownik findById(@PathVariable Long id) {
        logger.debug("Wywolano /api/pracownicy/{id} metoda HTTP GET, id={}", id);
        return pracownicyService.findById(id).orElseThrow(PracownikNieZnalezionyException::new);
    }

    @GetMapping({"/nazwa/{nazwa}"})
    public List<Pracownik> findByNazwa(@PathVariable String nazwa) {
        logger.debug("Wywolano /api/pracownicy/nazwa/{nazwa} metoda HTTP GET, nazwa={}", nazwa);
        return pracownicyService.findByNazwa(nazwa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pracownik create(@RequestBody Pracownik pracownik) {
        logger.debug("Wywolano /api/pracownicy metoda HTTP POST");
        return pracownicyService.save(pracownik);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.debug("Wywolano /api/pracownicy/{id} metoda HTTP DELETE, id={}", id);
        pracownicyService.findById(id).orElseThrow(PracownikNieZnalezionyException::new);
        pracownicyService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Pracownik update(@RequestBody Pracownik pracownik, @PathVariable Long id) {
        logger.debug("Wywolano /api/pracownicy/{id} metoda HTTP PUT, id={}", id);
        if (pracownik.getId() != id) {
            throw new PracownikIdNieprawidlowyException();
        }
        pracownicyService.findById(id).orElseThrow(PracownikNieZnalezionyException::new);
        return pracownicyService.save(pracownik);
    }

    @GetMapping("/createPdf")
    public void createPdf(HttpServletRequest request, HttpServletResponse response) {
        List<Pracownik> users = pracownicyService.getAll();
        boolean isFlag = pracownicyService.createPdf(users, context, request, response);
        logger.debug("Wywolano createPdf(), isFlag={}", isFlag);
        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "pracownicy" + ".pdf");
            filedownload(fullPath, response, "pracownicy.pdf");
        }
    }

    @GetMapping("/createExcel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Pracownik> pracownicy = pracownicyService.getAll();
        boolean isFlag = pracownicyService.createExcel(pracownicy, context, request, response);
        logger.debug("Wywolano createExcel(), isFlag={}", isFlag);
        if(isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "pracownicy" + ".xls");
            filedownload(fullPath, response, "pracownicy.xls");
        }
    }

    private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
        File file = new File(fullPath);
        final int ROZMIAR_BUFORA = 4096;
        if(file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment; filename=" + fileName);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[ROZMIAR_BUFORA];
                int bytesRead = -1;
                while((bytesRead = inputStream.read(buffer))!= -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
