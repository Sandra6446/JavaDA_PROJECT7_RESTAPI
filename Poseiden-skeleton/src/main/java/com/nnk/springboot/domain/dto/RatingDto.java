package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private Integer id;
    @NotBlank(message = "Moody's rating is mandatory")
    private String moodysRating;
    @NotBlank(message = "S&P rating is mandatory")
    private String sandPRating;
    @NotBlank(message = "Fitch rating is mandatory")
    private String fitchRating;
    @NotNull(message = "Order number is mandatory")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    public RatingDto(Rating rating) {
        this.id = rating.getId();
        this.moodysRating = rating.getMoodysRating();
        this.sandPRating = rating.getSandPRating();
        this.fitchRating = rating.getFitchRating();
        this.orderNumber = rating.getOrderNumber();
    }
}
