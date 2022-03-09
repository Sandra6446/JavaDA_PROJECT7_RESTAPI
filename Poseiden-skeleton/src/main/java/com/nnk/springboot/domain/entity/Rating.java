package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.RatingDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a rating (mapping class)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    /**
     * Build a Rating with RatingDto values
     *
     * @param ratingDto : The RatingDto with values to be copied
     */
    public Rating(RatingDto ratingDto) {
        this.id = ratingDto.getId();
        this.moodysRating = ratingDto.getMoodysRating();
        this.sandPRating = ratingDto.getSandPRating();
        this.fitchRating = ratingDto.getFitchRating();
        this.orderNumber = ratingDto.getOrderNumber();
    }

    /**
     * Updates a Rating with RatingDto values
     *
     * @param ratingDto : The RatingDto with values to be updated
     */
    public void updateFromDto(RatingDto ratingDto) {
        if (Objects.equals(ratingDto.getId(), this.getId())) {
            this.moodysRating = ratingDto.getMoodysRating();
            this.sandPRating = ratingDto.getSandPRating();
            this.fitchRating = ratingDto.getFitchRating();
            this.orderNumber = ratingDto.getOrderNumber();
        }
    }
}
