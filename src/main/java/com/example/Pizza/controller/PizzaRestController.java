package com.example.Pizza.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Pizza.model.Order;
import com.example.Pizza.service.PizzaService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/order")
public class PizzaRestController {
    
    @Autowired
    private PizzaService pizzaSvc;

    @GetMapping(path="{orderId}")
        public ResponseEntity<String> getOrderDetails(@PathVariable String orderId){
            Optional<Order> p = pizzaSvc.getOrderByOrderId(orderId);
            System.out.println("string --> " + p.get().toJSON().toString());
            //Optional<Order> ord = pizzaSvc.
            if(p.isEmpty()){
                JsonObject error = Json.createObjectBuilder()
                    .add("message", "Order %s not found".formatted(orderId))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error.toString());
            }
            
            return ResponseEntity.ok(p.get().toJSON().toString());
    }
}


