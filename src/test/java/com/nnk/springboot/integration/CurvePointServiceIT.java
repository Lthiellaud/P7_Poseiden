package com.nnk.springboot.integration;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/sql/data.sql")
public class CurvePointServiceIT {

    @Autowired
    private CurvePointService curvePointService;

    @Test
    public void createCurvePointTest() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(8.0);
        curvePoint.setValue(2.0);

        curvePointService.createCurvePoint(curvePoint);

        assertThat(curvePointService.getCurvePointById(3).getCurveId()).isEqualTo(1);
    }

    @Test
    public void updateCurvePointTest() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(8.0);
        curvePoint.setValue(2.0);

        curvePointService.updateCurvePoint(curvePoint, 1);

        assertThat(curvePointService.getCurvePointById(1).getCurveId()).isEqualTo(1);
        assertThat(curvePointService.getCurvePointById(1).getTerm()).isEqualTo(8.0);
        assertThat(curvePointService.getCurvePointById(1).getValue()).isEqualTo(2.0);

    }

    @Test
    public void deleteCurvePointTest() throws Exception {
        curvePointService.deleteCurvePoint(2);

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> curvePointService.getCurvePointById(2));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid curve point Id: 2");

    }
}
