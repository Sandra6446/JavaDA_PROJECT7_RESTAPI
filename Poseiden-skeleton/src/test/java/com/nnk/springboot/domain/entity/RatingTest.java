package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.RatingDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RatingTest {

    @Test
    public void updateFromDto() {
        RatingDto ratingDto = new RatingDto(1,"MR1","SPR1","FR1",1);
        Rating rating = new Rating(1,"MR2","SPR2","FR2",1);
        Rating updatedRating = new Rating(1,"MR1","SPR1","FR1",1);

        rating.updateFromDto(ratingDto);
        Assert.assertEquals(updatedRating,rating);

        // RatingDto with bad id
        ratingDto.setId(2);
        ratingDto.setOrderNumber(6);
        rating.updateFromDto(ratingDto);
        Assert.assertEquals(updatedRating,rating);
    }
}