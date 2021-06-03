package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    @NotNull(message = "must not be null")
    private Integer curveId ;
    private Timestamp asOfDate ;
    private Double term ;
    private Double value ;
    private Timestamp creationDate;
}
