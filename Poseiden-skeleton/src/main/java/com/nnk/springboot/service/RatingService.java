package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.domain.entity.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the Rating data
 */
@Service
@Transactional
@AllArgsConstructor
public class RatingService implements CrudService<RatingDto> {

    @Autowired
    RatingRepository ratingRepository;

    /**
     * Gets all Ratings in database
     *
     * @return A list of corresponding RatingDto
     */
    @Override
    public List<RatingDto> getAll() {
        List<Rating> ratings = ratingRepository.findAll();
        List<RatingDto> ratingDtos = new ArrayList<>();
        for (Rating rating : ratings) {
            ratingDtos.add(new RatingDto(rating));
        }
        return ratingDtos;
    }

    /**
     * Gets a specific Rating in database
     *
     * @param id : The id to be found
     * @return The corresponding RatingDto
     */
    @Override
    public RatingDto getById(Integer id) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        return new RatingDto(rating);
    }

    /**
     * Saves a Rating in database
     *
     * @param ratingDto : The RatingDto with values to be saved
     */
    @Override
    public void save(RatingDto ratingDto) {
        ratingRepository.saveAndFlush(new Rating(ratingDto));
    }

    /**
     * Updates a Rating in database
     *
     * @param ratingDto : The RatingDto with new values
     */
    @Override
    public void update(RatingDto ratingDto) {
        Rating rating = ratingRepository.findById(ratingDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid rating with id :" + ratingDto.getId()));
        rating.updateFromDto(ratingDto);
        ratingRepository.saveAndFlush(rating);
    }

    /**
     * Deletes a Rating in database
     *
     * @param id : The id of the Rating to be deleted
     */
    @Override
    public void delete(Integer id) {
        ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating with id :" + id));
        ratingRepository.deleteById(id);
    }
}
