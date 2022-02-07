package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.CurvePointDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer curveId;
    private Timestamp asOfDate;
    private double term;
    private double value;
    private Timestamp creationDate;

    public CurvePoint(Integer curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public CurvePoint(CurvePointDto curvePointDto) {
        this.id = curvePointDto.getId();
        this.curveId = curvePointDto.getCurveId();
        this.term = curvePointDto.getTerm();
        this.value = curvePointDto.getValue();
    }

    public CurvePoint(Integer id, Integer curveId, double term, double value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public void updateFromDto(CurvePointDto curvePointDto) {
        if (curvePointDto.getId().equals(this.id)) {
            this.setCurveId(curvePointDto.getCurveId());
            this.setTerm(curvePointDto.getTerm());
            this.setValue(curvePointDto.getValue());
        }
    }
}
