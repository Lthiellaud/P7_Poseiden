package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/sql/data.sql")
public class RatingServiceIT {

    @Autowired
    private RatingService ratingService;

    @Test
    public void createRatingTest() throws Exception {
        Rating rating = new Rating();
        rating.setMoodysRating("New moodysRating");
        rating.setSandPRating("SandPRating");
        rating.setFitchRating("New fitch");
        rating.setOrderNumber(0);

        ratingService.createRating(rating);

        assertThat(ratingService.getRatingById(3).getMoodysRating()).isEqualTo("New moodysRating");
    }

    @Test
    public void updateRatingTest() throws Exception {
        Rating rating = new Rating();
        rating.setMoodysRating("MoodysRating updated");
        rating.setSandPRating("SandPRating updated");
        rating.setFitchRating("Fitch");
        rating.setOrderNumber(1);

        ratingService.updateRating(rating, 1);

        assertThat(ratingService.getRatingById(1).getMoodysRating()).isEqualTo("MoodysRating updated");
        assertThat(ratingService.getRatingById(1).getSandPRating()).isEqualTo("SandPRating updated");
        assertThat(ratingService.getRatingById(1).getFitchRating()).isEqualTo("Fitch");
        assertThat(ratingService.getRatingById(1).getOrderNumber()).isEqualTo(1);

    }

    @Test
    public void deleteRatingTest() throws Exception {
        ratingService.deleteRating(2);

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ratingService.getRatingById(2));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid rating Id: 2");

    }
}
