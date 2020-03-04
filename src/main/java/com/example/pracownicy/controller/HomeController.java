package com.example.pracownicy.controller;

import com.example.pracownicy.repository.PracownicyRepository;
import com.example.pracownicy.service.PracownicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final PracownicyService pracownicyService;

    public HomeController(PracownicyService pracownicyService) {
        this.pracownicyService = pracownicyService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("pracownicy", pracownicyService.getAll());
        return "index";
    }

}
