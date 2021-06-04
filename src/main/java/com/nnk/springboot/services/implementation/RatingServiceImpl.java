package com.nnk.springboot.services.implementation;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Creates a new Rating.
     * @param rating the Rating to be created
     */
    @Override
    public void createRating(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * Updates a rating.
     * @param rating the rating to be updated
     * @param id id of the rating to be updated
     */
    @Override
    public void updateRating(Rating rating, Integer id) {
        Rating updatedRating = getRatingById(id);
        updatedRating.setMoodysRating(rating.getMoodysRating());
        updatedRating.setSandPRating(rating.getSandPRating());
        updatedRating.setFitchRating(rating.getFitchRating());
        ratingRepository.save(updatedRating);
    }

    /**
     * Gives all the ratings
     * @return all the ratings
     */
    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    /**
     * returns a rating from an id
     * @param id the id
     * @return the rating
     */
    @Override
    public Rating getRatingById(Integer id) throws IllegalArgumentException {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id: " + id));
    }

    /**
     * delete a rating from an id
     * @param id the id
     */
    @Override
    public void deleteRating(Integer id) throws IllegalArgumentException {
        ratingRepository.delete(getRatingById(id));
    }
}
