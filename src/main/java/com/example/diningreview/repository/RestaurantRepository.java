package com.example.diningreview.repository;

import com.example.diningreview.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    Optional<Restaurant> findById(Long id);
    List<Restaurant> findByZipCode(String zipCode);
    List<Restaurant> findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScoreDesc(String zipcode);
    List<Restaurant> findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScoreDesc(String zipcode);
    List<Restaurant> findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScoreDesc(String zipcode);
}
