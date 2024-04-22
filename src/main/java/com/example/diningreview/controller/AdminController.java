package com.example.diningreview.controller;

import com.example.diningreview.model.AdminReviewAction;
import com.example.diningreview.model.Restaurant;
import com.example.diningreview.model.Review;
import com.example.diningreview.model.ReviewStatus;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.example.diningreview.repository.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminController(final ReviewRepository reviewRepository, final UserRepository userRepository, final RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;

    }

    @GetMapping("/reviews")
    public List<Review> getReviewByStatus() {
        return this.reviewRepository.findReviewByStatus(ReviewStatus.PENDING);
    }

    @PutMapping("/reviews/{id}")
    public Review performAction(@PathVariable("id") Long id, @RequestBody AdminReviewAction adminReviewAction) {
        Optional<Review> checkReviewOptional = this.reviewRepository.findById(Math.toIntExact(id));
        if(checkReviewOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Review review = checkReviewOptional.get();
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        if(adminReviewAction.getAcceptReview()) {
            review.setStatus(ReviewStatus.ACCEPTED);
        } else {
            review.setStatus(ReviewStatus.REJECTED);
        }

        Review updatedReview = this.reviewRepository.save(review);
        updateScore(optionalRestaurant.get());
        return updatedReview;
    }

    public void updateScore(Restaurant restaurant) {
        List<Review> reviews = this.reviewRepository.findReviewByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.ACCEPTED);
        if(reviews.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        int peanutScore = 0;
        int eggScore = 0;
        int dairyScore = 0;

        int peanutCount = 0;
        int eggCount = 0;
        int dairyCount = 0;

        for(Review r : reviews) {
            if(!ObjectUtils.isEmpty(r.getPeanutScore())) {
                peanutScore += r.getPeanutScore();
                peanutCount++;
            }
            if(!ObjectUtils.isEmpty(r.getEggScore())) {
                eggScore += r.getEggScore();
                eggCount++;
            }
            if(!ObjectUtils.isEmpty(r.getDairyScore())) {
                dairyScore += r.getDairyScore();
                dairyCount++;
            }
        }

        int totalScore = peanutScore + eggScore + dairyScore;
        int totalCount = peanutCount + eggCount + dairyCount;

        float overall = (float) totalScore / totalCount;
        restaurant.setOverall(overall);

        if(peanutCount > 0) {
            float overallPeanutScore = (float) peanutScore / peanutCount;
            restaurant.setPeanutScore((int) overallPeanutScore);
        }
        if(eggCount > 0) {
            float overallEggScore = (float) eggScore / eggCount;
            restaurant.setEggScore((int) overallEggScore);
        }
        if(peanutCount > 0) {
            float overallDairyScore = (float) dairyScore / dairyCount;
            restaurant.setDairyScore((int) overallDairyScore);
        }

        this.restaurantRepository.save(restaurant);
    }
}
