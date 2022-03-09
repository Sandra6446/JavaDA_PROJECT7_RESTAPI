package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.CurvePoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * Represents a curvePoint
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDto {

    private Integer id;

    @NotNull(message = "Must not be null")
    @Positive(message = "Curve Id must be positive")
    private Integer curveId;

    @PositiveOrZero(message = "Term must be greater than or equal to zero")
    private Double term;

    @PositiveOrZero(message = "Value must be greater than or equal to zero")
    private Double value;

    /**
     * Build a CurvePointDto with CurvePoint values
     *
     * @param curvePoint : The CurvePoint with values to be copied
     */
    public CurvePointDto(CurvePoint curvePoint) {
        this.id = curvePoint.getId();
        this.curveId = curvePoint.getCurveId();
        this.term = curvePoint.getTerm();
        this.value = curvePoint.getValue();
    }
}
