package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Moodys rating is mandatory")
    @Column(name="moodysrating")
    private String moodysRating;
    @NotBlank(message = "SandP rating is mandatory")
    @Column(name="sandprating")
    private String sandPRating;
    @NotBlank(message = "Fitch rating is mandatory")
    @Column(name="fitchrating")
    private String fitchRating;
    @NotNull(message = "must not be null")
    @Column(name="ordernumber")
    private Integer orderNumber;
}
