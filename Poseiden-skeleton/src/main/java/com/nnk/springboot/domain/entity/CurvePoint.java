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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer curveId;
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    private Timestamp creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public CurvePoint(Integer id, Integer curveId, Double term, Double value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public CurvePoint(CurvePointDto curvePointDto) {
        this.curveId = curvePointDto.getCurveId();
        if (curvePointDto.getTerm() == null) {
            this.setTerm(0d);
        } else {
            this.setTerm(curvePointDto.getTerm());
        }
        if (curvePointDto.getValue() == null) {
            this.setValue(0d);
        } else {
            this.setValue(curvePointDto.getValue());
        }
    }

    public void updateFromDto(CurvePointDto curvePointDto) {
        if (curvePointDto.getId().equals(this.id)) {
            this.setCurveId(curvePointDto.getCurveId());
            if (curvePointDto.getTerm() == null) {
                this.setTerm(0d);
            } else {
                this.setTerm(curvePointDto.getTerm());
            }
            if (curvePointDto.getValue() == null) {
                this.setValue(0d);
            } else {
                this.setValue(curvePointDto.getValue());
            }
        }
    }
}
