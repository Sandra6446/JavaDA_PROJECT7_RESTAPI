package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.entity.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
