package com.example.Pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Pizza.model.Delivery;
import com.example.Pizza.model.Pizza;
import com.example.Pizza.service.PizzaService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller 
public class PizzaController {
    
    @Autowired
    private PizzaService pizzaSvc;

    @GetMapping(path="/")
    public String showLandingPage(Model m) {

        m.addAttribute("pizza", new Pizza());
        return "index";
    }

    @PostMapping(path="/pizza") 
    public String postPizza(Model m, HttpSession session, @Valid Pizza pizza, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "index";
        }

        //validates pizza order
        List<ObjectError> errors =  pizzaSvc.validatePizzaOrder(pizza);
        if(!errors.isEmpty()) {
            for(ObjectError e : errors) {
                bindingResult.addError(e);
            }
            return "index";
        }

        session.setAttribute("pizza", pizza);
        m.addAttribute("delivery", new Delivery());
        return "delivery";
    }


}
