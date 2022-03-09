package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Creates, reads, updates and deletes Ratings in database
 *
 * @see Rating
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
