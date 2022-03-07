package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.domain.entity.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointServiceTest {

    private CurvePointService curvePointService;

    @MockBean
    private CurvePointRepository curvePointRepository;

    private List<CurvePoint> curvePoints;
    private CurvePoint curvePoint;
    private List<CurvePointDto> curvePointDtos;
    private CurvePointDto curvePointDto;

    @Before
    public void setUp() {
        curvePoint = new CurvePoint(1, 5,10d,20d);
        CurvePoint curvePoint2 = new CurvePoint(2, 6, 10d,10d);
        CurvePoint curvePoint3 = new CurvePoint(3, 10,10.5,0.0);
        curvePoints = Arrays.asList(curvePoint, curvePoint2, curvePoint3);

        curvePointDto = new CurvePointDto(curvePoint);
        CurvePointDto curvePointDto2 = new CurvePointDto(curvePoint2);
        CurvePointDto curvePointDto3 = new CurvePointDto(curvePoint3);
        curvePointDtos = Arrays.asList(curvePointDto, curvePointDto2, curvePointDto3);

        curvePointService = new CurvePointService(curvePointRepository);
    }

    @Test
    public void getAll() {
        Mockito.when(curvePointRepository.findAll()).thenReturn(curvePoints);
        Assert.assertEquals(curvePointDtos, curvePointService.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getAll() {
        Mockito.when(curvePointRepository.findAll()).thenThrow(new IllegalArgumentException());
        curvePointService.getAll();
    }

    @Test
    public void getById() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(curvePoint));
        Assert.assertEquals(curvePointDto, curvePointService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        curvePointService.getById(1);
    }

    @Test
    public void save() {
        CurvePoint curvePointToSave = new CurvePoint(5,10d,20d);
        curvePointService.save(curvePointDto);
        verify(curvePointRepository,times(1)).saveAndFlush(curvePointToSave);
    }

    @Test
    public void update() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(curvePoint));
        curvePointService.update(curvePointDto);
        verify(curvePointRepository,times(1)).saveAndFlush(curvePoint);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        curvePointService.update(curvePointDto);
    }

    @Test
    public void delete() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(curvePoint));
        curvePointService.delete(1);
        verify(curvePointRepository,times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        curvePointService.delete(1);
    }
}