package com.softnet.shoplife.services;

import com.softnet.shoplife.dto.request.ReviewRequest;
import com.softnet.shoplife.dto.responses.ReviewResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ReviewService {



    List<ReviewResponse>  createReview(ReviewRequest reviewRequest, Long productid);

    ReviewResponse findReviewById(Long id);

    List<ReviewResponse> findAllReviews();

    List<ReviewResponse> findReviewByUsername(String username);
}
