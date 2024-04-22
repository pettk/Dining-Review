package com.example.diningreview.controller;

import com.example.diningreview.model.Restaurant;
import com.example.diningreview.repository.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    public RestaurantController(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping()
    public Restaurant creatRestaurant(@RequestBody Restaurant restaurant) {
        Optional<Restaurant> restaurantToCreate = this.restaurantRepository.findById(restaurant.getId());
        if(restaurantToCreate.isPresent()) {
            return null;
        }

        Restaurant newRestaurant = this.restaurantRepository.save(restaurant);
        return newRestaurant;
    }

    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurant(@PathVariable("id") Long id) {
        return this.restaurantRepository.findById(id);
    }
    @GetMapping()
    public Iterable<Restaurant> getAllRestaurant() {
        return this.restaurantRepository.findAll();
    }

    @GetMapping("/search")
    public Iterable<Restaurant> searchRestaurants(@RequestParam String zipCode, String allergy) {
        if(this.restaurantRepository.findByZipCode(zipCode).isEmpty()) {
            return null;
        }

        Iterable<Restaurant> restaurants = null;
        if(allergy.equalsIgnoreCase("peanut")) {
            restaurants = this.restaurantRepository.findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScoreDesc(zipCode);
        } else if (allergy.equalsIgnoreCase("egg")) {
            restaurants = this.restaurantRepository.findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScoreDesc(zipCode);
        } else if (allergy.equalsIgnoreCase("dairy")) {
            restaurants = this.restaurantRepository.findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScoreDesc(zipCode);
        }
        return restaurants;
    }

}
