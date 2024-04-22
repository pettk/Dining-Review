package com.example.diningreview.repository;

import com.example.diningreview.model.Review;
import com.example.diningreview.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findReviewByStatus(ReviewStatus status);
    List<Review> findReviewByRestaurantIdAndStatus(Long id, ReviewStatus status);
}
