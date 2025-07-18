package com.softnet.shoplife.controller;

import com.softnet.shoplife.dto.request.ReviewRequest;
import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.ReviewResponse;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.exceptions.NotFoundException;
import com.softnet.shoplife.services.ReviewService;
import com.softnet.shoplife.utils.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {

@Autowired
  private     ReviewService reviewService;

    //Create New Review
    @PostMapping("/create/{productid}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> createReview(@RequestBody ReviewRequest reviewRequest, @PathVariable("productid") Long productid) {

        try {


            List<ReviewResponse> response = reviewService.createReview(reviewRequest, productid);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
        }catch (InvalidRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating Review", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create Review", null));
        }
        }


    //Get Review By Review ID
    @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ReviewResponse>> findReviewById(@PathVariable("id") Long id) {


        try{
            ReviewResponse response =  reviewService.findReviewById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Getting Review By ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Get Review By ID", null));
        }


    }

//Get All Reviews
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> findAllReviews(){

        try {
            List<ReviewResponse> response = reviewService.findAllReviews();
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Getting All Reviews", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Get Reviews", null));
        }
    }

    //Get Review By Username
    @GetMapping("/get/by/username/{username}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> findReviewByUsername(@PathVariable("username") String username) {
       try {
           List<ReviewResponse> response = reviewService.findReviewByUsername(username);
           return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
       }catch (NotFoundException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
               .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
       } catch (Exception e) {
           log.error("Username Does Not Exist", e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Get Reviews By Username", null));
       }
    }


}
