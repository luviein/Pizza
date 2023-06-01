package com.example.Pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Pizza.model.Delivery;
import com.example.Pizza.model.Order;
import com.example.Pizza.model.Pizza;
import com.example.Pizza.service.PizzaService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping(consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class PizzaController {
    
    @Autowired
    private PizzaService pizzaSvc;

   

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

    @PostMapping(path="/pizza/order") 
        public String postPizzaOrder(Model model, HttpSession session, @Valid Delivery delivery, BindingResult bindingResult){

            if(bindingResult.hasErrors()){
                return "delivery";
            }

            Pizza p = (Pizza) session.getAttribute("pizza");
            Order o = pizzaSvc.savePizza(p, delivery);
            model.addAttribute("order", o);
            return"order";

        }
    


}
