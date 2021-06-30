package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CurvePointServiceTest {

    @MockBean
    private CurvePointRepository curvePointRepository;

    @Autowired
    private CurvePointService curvePointService;

    private static CurvePoint curvePoint;

    @BeforeEach
    public void initTest() {
        curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(8.0);
        curvePoint.setValue(2.0);
        curvePoint.setId(1);
    }

    @Test
    void createCurvePointTest() throws Exception {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
        curvePointService.createCurvePoint(curvePoint);

        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    void updateCurvePointTest() throws Exception {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
        curvePointService.updateCurvePoint(curvePoint, 1);

        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    void getCurvePointByIdTest() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        CurvePoint curvePoint1 = curvePointService.getCurvePointById(1);

        verify(curvePointRepository, times(1)).findById(1);
        assertThat(curvePoint1).isEqualTo(curvePoint);
    }

    @Test
    void getCurvePointByIdNotFoundedTest() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> curvePointService.getCurvePointById(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid curve point Id: 1");
        verify(curvePointRepository, times(1)).findById(1);

    }

    @Test
    void deleteCurvePointNotFoundedTest() throws Exception {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> curvePointService.deleteCurvePoint(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid curve point Id: 1");
        verify(curvePointRepository, times(1)).findById(1);

    }
}