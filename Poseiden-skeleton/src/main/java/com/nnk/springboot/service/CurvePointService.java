package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.domain.entity.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CurvePointService implements CrudService<CurvePointDto> {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePointDto> getAll() {
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        List<CurvePointDto> curvePointDtos = new ArrayList<>();
        for (CurvePoint curvePoint : curvePoints) {
            curvePointDtos.add(new CurvePointDto(curvePoint));
        }
        return curvePointDtos;
    }

    @Override
    public CurvePointDto getById(Integer id) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curve Id:" + id));
        return new CurvePointDto(curvePoint);
    }

    @Override
    public void save(CurvePointDto curvePointDto) {
        curvePointRepository.saveAndFlush(new CurvePoint(curvePointDto));
    }

    @Override
    public void update(CurvePointDto curvePointDto) {
        CurvePoint curvePoint = curvePointRepository.findById(curvePointDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid curve with id :" + curvePointDto.getId()));
        curvePoint.updateFromDto(curvePointDto);
        curvePointRepository.saveAndFlush(curvePoint);
    }

    @Override
    public void delete(Integer id) {
        curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Curve with id :" + id));
        curvePointRepository.deleteById(id);
    }
}
