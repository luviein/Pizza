package com.example.Pizza.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import com.example.Pizza.model.Delivery;
import com.example.Pizza.model.Order;
import com.example.Pizza.model.Pizza;
import com.example.Pizza.repository.PizzaRepository;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository repository;

    // @Value("${pizza.app.api.url}")
    // private String restPizzaUrl;
    
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

    public Order savePizza(Pizza p, Delivery d){
        Order o = createPizzaOrder(p, d);
        System.out.println(calculateCost(o));
        calculateCost(o);
        repository.save(o);
        return o;
    }

    private Order createPizzaOrder(Pizza p, Delivery d){
        String orderId = UUID.randomUUID().toString().substring(0,8);
        Order o = new Order(p,d);
        o.setOrderId(orderId);
        return o;
    }

    private double calculateCost(Order o){
        double total = 0;

        switch(o.getPizzaName()){
            case "margherita":
                total += 22;
                break;
            case "trioformaggio":
                total += 25;
                break;
            case "bella", "marinara", "spianatacalabrese":
                total += 30;
                break;
        }

        switch(o.getSize()){
            case "md":
                total *= 1.2;
                break;
            case "lg":
                total *= 1.5;
                break;
            case "sm":
                total *= 1;
                break;
        }

        total *= o.getQuantity();
        if(o.getRush()){
            total += 2;
        }
        o.setTotalCost(total);
        return total;
    }
    // public Optional <Order> getOrderDetails(String orderId){
    //     String url = UriComponentsBuilder
    //                 .fromUriString(this.restPizzaUrl + orderId)
    //                 .toUriString();
        
    //     RequestEntity req = RequestEntity.get(url).build();
    //     RestTemplate rTemplate = new RestTemplate();
    // }

    public Optional <Order> getOrderByOrderId (String orderId){
        return this.repository.get(orderId);
    }
}
