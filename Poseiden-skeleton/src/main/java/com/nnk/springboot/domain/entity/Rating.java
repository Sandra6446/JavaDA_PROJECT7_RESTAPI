package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.RatingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Rating(RatingDto ratingDto) {
        this.moodysRating = ratingDto.getMoodysRating();
        this.sandPRating = ratingDto.getSandPRating();
        this.fitchRating = ratingDto.getFitchRating();
        this.orderNumber = ratingDto.getOrderNumber();
    }

    public void updateFromDto(RatingDto ratingDto) {
        if (ratingDto.getId().equals(this.id)) {
            this.moodysRating = ratingDto.getMoodysRating();
            this.sandPRating = ratingDto.getSandPRating();
            this.fitchRating = ratingDto.getFitchRating();
            this.orderNumber = ratingDto.getOrderNumber();
        }
    }
}
