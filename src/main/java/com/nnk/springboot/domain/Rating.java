package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    private String moodysRating ;
    private String sandPRating ;
    private String fitchRating ;
    private Integer orderNumber ;
}
