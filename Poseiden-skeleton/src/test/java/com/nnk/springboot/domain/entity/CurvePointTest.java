package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.CurvePointDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTest {

    @Test
    public void updateFromDto() {
        CurvePointDto curvePointDto = new CurvePointDto(1,5,10.0, 10.0);
        CurvePoint curvePoint = new CurvePoint(1,10,10.0,20.0);
        CurvePoint updatedCurvePoint = new CurvePoint(1,5,10.0, 10.0);

        curvePoint.updateFromDto(curvePointDto);
        Assert.assertEquals(updatedCurvePoint,curvePoint);

        // CurvePointDto with bad id
        curvePointDto.setId(2);
        curvePointDto.setValue(5.0);
        curvePoint.updateFromDto(curvePointDto);
        Assert.assertEquals(updatedCurvePoint,curvePoint);
    }
}