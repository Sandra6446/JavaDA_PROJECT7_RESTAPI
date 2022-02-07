package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.CurvePoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDto {

    private Integer id;
    @NotNull(message = "Must not be null")
    @Positive(message = "Curve Id must be positive")
    private Integer curveId;
    @NotNull(message = "Term is mandatory")
    @PositiveOrZero(message = "Term must be greater than or equal to zero")
    private Double term;
    @NotNull(message = "Value is mandatory")
    @PositiveOrZero(message = "Value must be greater than or equal to zero")
    private Double value;

    public CurvePointDto(CurvePoint curvePoint) {
        this.id = curvePoint.getId();
        this.curveId = curvePoint.getCurveId();
        this.term = curvePoint.getTerm();
        this.value = curvePoint.getValue();
    }

}
