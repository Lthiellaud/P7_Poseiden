package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {
    void createRating(Rating rating) throws Exception;
    void updateRating(Rating rating, Integer id) throws Exception;
    List<Rating> getAllRating();
    Rating getRatingById(Integer id);
    void deleteRating(Integer id) throws Exception;
}
