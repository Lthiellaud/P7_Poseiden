package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {
    void createCurvePoint(CurvePoint curve) throws Exception;
    void updateCurvePoint(CurvePoint curve, Integer id) throws Exception;
    List<CurvePoint> getAllCurvePoint();
    CurvePoint getCurvePointById(Integer id);
    void deleteCurvePoint(Integer id) throws Exception;
}
