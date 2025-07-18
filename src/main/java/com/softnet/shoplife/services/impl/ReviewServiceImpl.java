package com.softnet.shoplife.services.impl;

import com.softnet.shoplife.dto.request.ReviewRequest;
import com.softnet.shoplife.dto.responses.ReviewResponse;
import com.softnet.shoplife.entity.ProcessedProducts;
import com.softnet.shoplife.entity.ProductReviews;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.exceptions.NotFoundException;
import com.softnet.shoplife.repository.ProProductRepository;
import com.softnet.shoplife.repository.ReviewRepository;
import com.softnet.shoplife.services.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProProductRepository proProductRepository;



    @Override
    public List<ReviewResponse>  createReview(ReviewRequest reviewRequest, Long productid) {


        Optional<ProcessedProducts> productOptional = proProductRepository.findById(productid);
        if (productOptional.isEmpty()) {


           throw new InvalidRequestException("Invalid productid");
        }


           ProductReviews productReviews = new ProductReviews();

        productReviews.setComment(reviewRequest.getComment());
        productReviews.setUsername(reviewRequest.getUsername());
        productReviews.setProductName(reviewRequest.getProductName());
        productReviews.setProductPrimaryImage(reviewRequest.getProductPrimaryImage());
        productReviews.setProcessedProducts(productOptional.get());

            ProductReviews review = reviewRepository.save(productReviews);

            ReviewResponse  reviewResponse = new ReviewResponse();

            reviewResponse.setUsername(review.getUsername());
            reviewResponse.setComment(review.getComment());
            reviewResponse.setId(review.getId());
            reviewResponse.setProductName(review.getProductName());
            reviewResponse.setProductPrimaryImage(review.getProductPrimaryImage());

        List<ReviewResponse> responseList = new ArrayList<>();
        responseList.add(reviewResponse);

        return responseList;

    }

    @Override
    public ReviewResponse findReviewById(Long id) {

       Optional<ProductReviews> response = reviewRepository.findById(id);
       if (response.isEmpty()) {
           throw  new NotFoundException("Invalid review id");
       }else{
           ReviewResponse reviewResponse = new ReviewResponse();
           reviewResponse.setUsername(response.get().getUsername());
           reviewResponse.setComment(response.get().getComment());
           reviewResponse.setId(response.get().getId());
           reviewResponse.setProductName(response.get().getProductName());
           reviewResponse.setProductPrimaryImage(response.get().getProductPrimaryImage());
           reviewResponse.setProcessedProductsId(response.get().getProcessedProducts().getId());
           return reviewResponse;
       }

    }

    @Override
    public List<ReviewResponse> findAllReviews() {
         List<ProductReviews> productReviews = reviewRepository.findAll();
         List<ReviewResponse> reviewResponses = new ArrayList<>();
         for (ProductReviews productReview : productReviews) {
             ReviewResponse reviewResponse = new ReviewResponse();
             reviewResponse.setUsername(productReview.getUsername());
             reviewResponse.setComment(productReview.getComment());
             reviewResponse.setId(productReview.getId());
             reviewResponse.setProductName(productReview.getProductName());
             reviewResponse.setProductPrimaryImage(productReview.getProductPrimaryImage());
reviewResponse.setProcessedProductsId(productReview.getProcessedProducts().getId());
             reviewResponses.add(reviewResponse);

         }
         return reviewResponses;
    }

    @Override
    public List<ReviewResponse> findReviewByUsername(String username) {
        List<ProductReviews> reviews = reviewRepository.findByUsername(username);
        if (reviews.isEmpty()) {
            throw new NotFoundException("Invalid username");
        } else {
            List<ReviewResponse> reviewResponseArray = new ArrayList<>();
            for (ProductReviews productReview : reviews) {
                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setUsername(productReview.getUsername());
                reviewResponse.setComment(productReview.getComment());
                reviewResponse.setId(productReview.getId());
                reviewResponse.setProductName(productReview.getProductName());
                reviewResponse.setProductPrimaryImage(productReview.getProductPrimaryImage());
                reviewResponse.setProcessedProductsId(productReview.getProcessedProducts().getId());
                reviewResponseArray.add(reviewResponse);

            }

            return reviewResponseArray;

        }
    }

}
