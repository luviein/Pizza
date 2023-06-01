package com.example.Pizza.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.example.Pizza.model.Pizza;
import com.example.Pizza.repository.PizzaRepository;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository repository;
    
    public static final String[] PIZZA_NAMES = {
        "bella",
        "margherita",
        "marinara",
        "spianatacalabrese",
        "trioformaggio"
    };

    public final static String[] PIZZA_SIZE = {
        "sm",
        "md",
        "lg"
    };

    private final Set<String>pizzaNames;
    private final Set<String>pizzaSizes;

    public PizzaService(){
        pizzaNames = new HashSet<String>(Arrays.asList(PIZZA_NAMES));
        pizzaSizes = new HashSet<String>(Arrays.asList(PIZZA_SIZE));
    }

    public List<ObjectError> validatePizzaOrder(Pizza p) {
        List<ObjectError> errors = new LinkedList<>();
        FieldError error;
        if(!pizzaNames.contains(p.getPizza().toLowerCase())){
            //object name is the name that groups the relevant fields
            error = new FieldError("pizza", "pizza", "We do not have %s pizza".formatted(p.getPizza()));
            errors.add(error);
        }

        if(!pizzaSizes.contains(p.getSize().toLowerCase())) {
            error = new FieldError("pizza", "size", "We do not have %s size".formatted(p.getSize()));
            errors.add(error);
        }
        return errors;
    }
}
