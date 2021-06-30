package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;


/**
 * Trade
 */
@Entity
@Table(name = "trade")
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="tradeid")
    private Integer tradeId ;
    @NotBlank(message = "Account is mandatory")
    private String account ;
    @NotBlank(message = "Type is mandatory")
    private String type ;
    @Column(name="buyquantity")
    private Double buyQuantity ;
    @Column(name="sellquantity")
    private Double sellQuantity ;
    @Column(name="buyprice")
    private Double buyPrice ;
    @Column(name="sellprice")
    private Double sellPrice ;
    private String benchmark ;
    @Column(name="tradedate")
    private Timestamp tradeDate ;
    private String security ;
    private String status ;
    private String trader ;
    private String book ;
    @Column(name="creationname")
    private String creationName ;
    @Column(name="creationdate")
    private Timestamp creationDate ;
    @Column(name="revisionname")
    private String revisionName ;
    @Column(name="revisiondate")
    private Timestamp revisionDate ;
    @Column(name="dealname")
    private String dealName ;
    @Column(name="dealtype")
    private String dealType ;
    @Column(name="sourcelistid")
    private String sourceListId ;
    private String side ;
}
