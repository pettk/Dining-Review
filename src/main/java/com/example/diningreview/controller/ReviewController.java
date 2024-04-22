package com.example.diningreview.controller;

import com.example.diningreview.model.Restaurant;
import com.example.diningreview.model.Review;
import com.example.diningreview.model.User;
import com.example.diningreview.repository.RestaurantRepository;
import com.example.diningreview.repository.ReviewRepository;
import com.example.diningreview.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(final ReviewRepository reviewRepository, final UserRepository userRepository, final RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping()
    public Review addUserReview(@RequestBody Review r) {
        Optional<User> checkUser = this.userRepository.findUserByDisplayName(r.getReviewBy());
        if(checkUser.isEmpty()) {
            return null;
        }

        Optional<Restaurant> checkRestaureant = this.restaurantRepository.findById(r.getRestaurantId());
        if(checkRestaureant.isEmpty()) {
            return null;
        }

        return this.reviewRepository.save(r);
    }

}

