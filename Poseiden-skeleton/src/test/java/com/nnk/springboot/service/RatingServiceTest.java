package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.domain.entity.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RatingServiceTest {

    private RatingService ratingService;

    @MockBean
    private RatingRepository ratingRepository;

    private List<Rating> ratings;
    private Rating rating;
    private List<RatingDto> ratingDtos;
    private RatingDto ratingDto;

    @Before
    public void setUp() {
        rating = new Rating(1, "MR1", "SPR1", "FR1", 1);
        Rating rating2 = new Rating(2, "MR2", "SPR2", "FR2", 2);
        Rating rating3 = new Rating(3, "MR3", "SPR3", "FR3", 3);
        ratings = Arrays.asList(rating, rating2, rating3);

        ratingDto = new RatingDto(rating);
        RatingDto ratingDto2 = new RatingDto(rating2);
        RatingDto ratingDto3 = new RatingDto(rating3);
        ratingDtos = Arrays.asList(ratingDto, ratingDto2, ratingDto3);

        ratingService = new RatingService(ratingRepository);
    }

    @Test
    public void getAll() {
        Mockito.when(ratingRepository.findAll()).thenReturn(ratings);
        Assert.assertEquals(ratingDtos, ratingService.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getAll() {
        Mockito.when(ratingRepository.findAll()).thenThrow(new IllegalArgumentException());
        ratingService.getAll();
    }

    @Test
    public void getById() {
        Mockito.when(ratingRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(rating));
        Assert.assertEquals(ratingDto, ratingService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(ratingRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        ratingService.getById(1);
    }

    @Test
    public void save() {
        Rating ratingToSave = new Rating("MR1", "SPR1", "FR1", 1);
        ratingService.save(ratingDto);
        verify(ratingRepository, times(1)).saveAndFlush(ratingToSave);
    }

    @Test
    public void update() {
        Mockito.when(ratingRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(rating));
        ratingService.update(ratingDto);
        verify(ratingRepository, times(1)).saveAndFlush(rating);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(ratingRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        ratingService.update(ratingDto);
    }

    @Test
    public void delete() {
        Mockito.when(ratingRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(rating));
        ratingService.delete(1);
        verify(ratingRepository, times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(ratingRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        ratingService.delete(1);
    }
}