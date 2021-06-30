package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RatingServiceTest {

    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    private static Rating rating;

    @BeforeEach
    public void initTest() {
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("");
        rating.setSandPRating("");
        rating.setFitchRating("");
        rating.setOrderNumber(0);
    }

    @Test
    void createRatingTest() throws Exception {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        ratingService.createRating(rating);

        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    void updateRatingTest() throws Exception {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        ratingService.updateRating(rating, 1);

        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    void getRatingByIdTest() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        Rating rating1 = ratingService.getRatingById(1);

        verify(ratingRepository, times(1)).findById(1);
        assertThat(rating1).isEqualTo(rating);
    }

    @Test
    void getRatingByIdNotFoundedTest() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ratingService.getRatingById(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid rating Id: 1");
        verify(ratingRepository, times(1)).findById(1);

    }

    @Test
    void deleteRatingNotFoundedTest() throws Exception {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ratingService.deleteRating(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid rating Id: 1");
        verify(ratingRepository, times(1)).findById(1);

    }
}