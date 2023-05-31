package com.example.Pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Pizza.model.Pizza;

@Controller 
public class PizzaController {
    
    @GetMapping(path="/")
    public String showLandingPage(Model m) {

        m.addAttribute("pizza", new Pizza());
        return "index";
    }
}
