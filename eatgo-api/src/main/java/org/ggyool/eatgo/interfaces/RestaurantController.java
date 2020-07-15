package org.ggyool.eatgo.interfaces;

import org.ggyool.eatgo.domain.Restaurant;
import org.ggyool.eatgo.domain.RestaurantRepository;
import org.ggyool.eatgo.domain.RestaurantRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository repository;

    @GetMapping("/restaurants")
    public List<Restaurant> list(){
        List<Restaurant> restaurants = repository.findAll();
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable Long id){
        Restaurant restaurant = repository.findById(id);
        return restaurant;
    }

}