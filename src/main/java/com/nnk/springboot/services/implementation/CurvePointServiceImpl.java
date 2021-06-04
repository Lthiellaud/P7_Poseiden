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
    public void createCurvePoint(CurvePoint curvePoint) {
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        curvePointRepository.save(curvePoint);
    }

    /**
     * Updates a bid list.
     * @param curvePoint the bid list to be updated
     * @param id id of the curvePoint to be updated
     */
    @Override
    public void updateCurvePoint(CurvePoint curvePoint, Integer id) {
        CurvePoint updatedCurvePoint = getCurvePointById(id);
        updatedCurvePoint.setCurveId(curvePoint.getCurveId());
        updatedCurvePoint.setTerm(curvePoint.getTerm());
        updatedCurvePoint.setValue(curvePoint.getValue());
        curvePointRepository.save(updatedCurvePoint);
    }

    /**
     * Gives all the bid lists
     * @return all the bid lists
     */
    @Override
    public List<CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    /**
     * returns a bid list from an id
     * @param id the id
     * @return the bid list
     */
    @Override
    public CurvePoint getCurvePointById(Integer id) throws IllegalArgumentException {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id: " + id));
    }

    /**
     * delete a bid list from an id
     * @param id the id
     */
    @Override
    public void deleteCurvePoint(Integer id) throws IllegalArgumentException {
        curvePointRepository.delete(getCurvePointById(id));
    }
}
