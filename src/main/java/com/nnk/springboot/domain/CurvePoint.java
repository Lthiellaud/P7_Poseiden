package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
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
