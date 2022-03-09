package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.BidListDto;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents a bidList (mapping class)
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BidListId;
    private String account;
    private String type;
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
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

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public BidList(Integer bidListId, String account, String type, Double bidQuantity) {
        this.BidListId = bidListId;
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    /**
     * Build a BidList with BidListDto values
     *
     * @param bidListDto : The BidListDto with values to be copied
     */
    public BidList(BidListDto bidListDto) {
        this.BidListId = bidListDto.getBidListId();
        this.account = bidListDto.getAccount();
        this.type = bidListDto.getType();
        if (bidListDto.getBidQuantity() == null) {
            this.setBidQuantity(0d);
        } else {
            this.setBidQuantity(bidListDto.getBidQuantity());
        }
    }

    /**
     * Updates a BidList with BidListDto values
     *
     * @param bidListDto : The BidListDto with values to be updated
     */
    public void updateFromDto(BidListDto bidListDto) {
        if (Objects.equals(bidListDto.getBidListId(), this.getBidListId())) {
            this.setAccount(bidListDto.getAccount());
            this.setType(bidListDto.getType());
            if (bidListDto.getBidQuantity() == null) {
                this.setBidQuantity(0d);
            } else {
                this.setBidQuantity(bidListDto.getBidQuantity());
            }
        }
    }
}
