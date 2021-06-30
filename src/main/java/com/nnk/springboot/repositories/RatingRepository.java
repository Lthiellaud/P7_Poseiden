package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * gives access to Rating records.
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
