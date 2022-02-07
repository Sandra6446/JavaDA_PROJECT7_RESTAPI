package com.nnk.springboot.domain;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer CurveId;
    private Timestamp asOfDate;
    private double term;
    private double value;
    private Timestamp creationDate;
}
