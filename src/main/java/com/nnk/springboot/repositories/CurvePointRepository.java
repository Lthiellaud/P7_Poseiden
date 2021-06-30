package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * gives access to CurvePoint records.
 */
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
