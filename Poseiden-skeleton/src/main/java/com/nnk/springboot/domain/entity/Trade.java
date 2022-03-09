package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.TradeDto;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents a trade (mapping class)
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TradeId;
    private String account;
    private String type;
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
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

    /**
     * Build a Trade with TradeDto values
     *
     * @param tradeDto : The TradeDto with values to be copied
     */
    public Trade(TradeDto tradeDto) {
        this.TradeId = tradeDto.getTradeId();
        this.account = tradeDto.getAccount();
        this.type = tradeDto.getType();
        if (tradeDto.getBuyQuantity() == null) {
            this.setBuyQuantity(0d);
        } else {
            this.setBuyQuantity(tradeDto.getBuyQuantity());
        }
    }

    /**
     * Updates a Trade with TradeDto values
     *
     * @param tradeDto : The TradeDto with values to be updated
     */
    public void updateFromDto(TradeDto tradeDto) {
        if (Objects.equals(tradeDto.getTradeId(), this.getTradeId())) {
            this.setAccount(tradeDto.getAccount());
            this.setType(tradeDto.getType());
            if (tradeDto.getBuyQuantity() == null) {
                this.setBuyQuantity(0d);
            } else {
                this.setBuyQuantity(tradeDto.getBuyQuantity());
            }
        }
    }
}
