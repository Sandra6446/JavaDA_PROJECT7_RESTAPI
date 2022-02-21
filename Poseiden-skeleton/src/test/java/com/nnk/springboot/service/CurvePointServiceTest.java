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
    private List<CurvePointDto> curvePointDtos;

    @Before
    public void setUp() {
        CurvePoint curvePoint1 = new CurvePoint(1, 5,10d,20d);
        CurvePoint curvePoint2 = new CurvePoint(2, 6, 10d,10d);
        CurvePoint curvePoint3 = new CurvePoint(3, 10,10.5,0.0);
        curvePoints = Arrays.asList(curvePoint1, curvePoint2, curvePoint3);
        curvePointService = new CurvePointService(curvePointRepository);

        CurvePointDto curvePointDto1 = new CurvePointDto(1, 5,10d,20d);
        CurvePointDto curvePointDto2 = new CurvePointDto(2, 6, 10d,10d);
        CurvePointDto curvePointDto3 = new CurvePointDto(3, 10,10.5,0.0);
        curvePointDtos = Arrays.asList(curvePointDto1, curvePointDto2, curvePointDto3);
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
        CurvePointDto curvePointDto = new CurvePointDto(1, 5,10d,20d);
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(curvePoints.get(0)));
        Assert.assertEquals(curvePointDto, curvePointService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        curvePointService.getById(1);
    }

    @Test
    public void save() {
        CurvePointDto curvePointDto = curvePointDtos.get(0);
        CurvePoint curvePoint = curvePoints.get(0);
        curvePointService.save(curvePointDto);
        verify(curvePointRepository,times(1)).saveAndFlush(curvePoint);
    }

    @Test
    public void update() {
        CurvePointDto curvePointDto = curvePointDtos.get(0);
        CurvePoint curvePoint = curvePoints.get(0);
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(curvePoint));
        curvePointService.update(curvePointDto);
        verify(curvePointRepository,times(1)).saveAndFlush(curvePoint);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        curvePointService.update(curvePointDtos.get(0));
    }

    @Test
    public void delete() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(curvePoints.get(0)));
        curvePointService.delete(1);
        verify(curvePointRepository,times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(curvePointRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        curvePointService.delete(1);
    }
}