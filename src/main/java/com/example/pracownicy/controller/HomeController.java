package com.example.pracownicy.controller;

import com.example.pracownicy.repository.PracownicyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    final PracownicyRepository pracownicyRepository;

    public HomeController(PracownicyRepository pracownicyRepository) {
        this.pracownicyRepository = pracownicyRepository;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("pracownicy", pracownicyRepository.findAll());
        return "index";
    }

}
