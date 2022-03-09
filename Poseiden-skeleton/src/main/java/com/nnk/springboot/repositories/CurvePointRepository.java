package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.entity.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Creates, reads, updates and deletes Curvepoints in database
 *
 * @see CurvePoint
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
