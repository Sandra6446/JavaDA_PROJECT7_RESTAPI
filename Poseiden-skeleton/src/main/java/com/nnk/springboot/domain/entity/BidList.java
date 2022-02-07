package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.BidListDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {

    // TODO : Controler creationName et revisionName cf securite

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BidListId;
    private String account;
    private String type;
    private double bidQuantity;
    private double askQuantity;
    private double bid;
    private double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public BidList(Integer bidListId, String account, String type, double bidQuantity) {
        this.BidListId = bidListId;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public BidList(BidListDto bidListDto) {
        this.BidListId = bidListDto.getBidListId();
        this.account = bidListDto.getAccount();
        this.type = bidListDto.getType();
        this.bidQuantity = bidListDto.getBidQuantity();
    }

    public void updateFromDto(BidListDto bidListDto) {
        if (bidListDto.getBidListId().equals(this.BidListId)) {
            this.setAccount(bidListDto.getAccount());
            this.setType(bidListDto.getType());
            this.setBidQuantity(bidListDto.getBidQuantity());
        }
    }
}
