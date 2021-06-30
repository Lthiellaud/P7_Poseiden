package com.nnk.springboot.services.implementation;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Creates a new CurvePoint.
     * @param curvePoint the CurvePoint to be created
     */
    @Override
    public void createCurvePoint(CurvePoint curvePoint) throws Exception {
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        curvePointRepository.save(curvePoint);
    }

    /**
     * Updates a curve point.
     * @param curvePoint the curve point to be updated
     * @param id id of the curvePoint to be updated
     */
    @Override
    public void updateCurvePoint(CurvePoint curvePoint, Integer id) throws Exception {
        CurvePoint updatedCurvePoint = getCurvePointById(id);
        updatedCurvePoint.setCurveId(curvePoint.getCurveId());
        updatedCurvePoint.setTerm(curvePoint.getTerm());
        updatedCurvePoint.setValue(curvePoint.getValue());
        curvePointRepository.save(updatedCurvePoint);
    }

    /**
     * Gives all the curve points
     * @return all the curve points
     */
    @Override
    public List<CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    /**
     * returns a curve point from an id
     * @param id the id
     * @return the curve point
     */
    @Override
    public CurvePoint getCurvePointById(Integer id) throws IllegalArgumentException {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id: " + id));
    }

    /**
     * delete a curve point from an id
     * @param id the id
     */
    @Override
    public void deleteCurvePoint(Integer id) throws Exception {
        curvePointRepository.delete(getCurvePointById(id));
    }
}
