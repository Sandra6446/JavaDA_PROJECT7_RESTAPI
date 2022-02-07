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

@Service
@Transactional
@AllArgsConstructor
public class RatingService implements CrudService<RatingDto> {

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public List<RatingDto> getAll() {
        List<Rating> ratings = ratingRepository.findAll();
        List<RatingDto> ratingDtos = new ArrayList<>();
        for (Rating rating : ratings) {
            ratingDtos.add(new RatingDto(rating));
        }
        return ratingDtos;
    }

    @Override
    public RatingDto getById(Integer id) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        return new RatingDto(rating);
    }

    @Override
    public void save(RatingDto ratingDto) {
        ratingRepository.saveAndFlush(new Rating(ratingDto));
    }

    @Override
    public void update(RatingDto ratingDto) {
        Rating rating = ratingRepository.findById(ratingDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid rating with id :" + ratingDto.getId()));
        rating.updateFromDto(ratingDto);
        ratingRepository.saveAndFlush(rating);
    }

    @Override
    public void delete(Integer id) {
        ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating with id :" + id));
        ratingRepository.deleteById(id);
    }
}
