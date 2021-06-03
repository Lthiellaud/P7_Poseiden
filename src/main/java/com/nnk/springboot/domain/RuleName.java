package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    @NotBlank(message = "Name is mandatory")
    private String name ;
    private String description ;
    private String json ;
    private String template ;
    private String sqlStr ;
    private String sqlPart ;
}
