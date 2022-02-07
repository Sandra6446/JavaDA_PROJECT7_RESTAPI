package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.TradeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer TradeId;
    private String account;
    private String type;
    private double buyQuantity;
    private double sellQuantity;
    private double buyPrice;
    private double sellPrice;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public Trade(String account, String type, double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    public Trade(Integer TradeId, String account, String type, double buyQuantity) {
        this.TradeId = TradeId;
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    public Trade(TradeDto tradeDto) {
        this.TradeId = tradeDto.getTradeId();
        this.account = tradeDto.getAccount();
        this.type = tradeDto.getType();
        this.buyQuantity = tradeDto.getBuyQuantity();
    }

    public void updateFromDto(TradeDto tradeDto) {
        if (tradeDto.getTradeId().equals(this.TradeId)) {
            this.setAccount(tradeDto.getAccount());
            this.setType(tradeDto.getType());
            this.setBuyQuantity(tradeDto.getBuyQuantity());
        }
    }
}