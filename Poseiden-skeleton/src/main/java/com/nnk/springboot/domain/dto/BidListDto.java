package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.BidList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * Represents a bid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidListDto {

    private Integer BidListId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @PositiveOrZero(message = "Value must be greater than or equal to zero")
    private Double bidQuantity;

    /**
     * Build a BidListDto with BidList values
     *
     * @param bidList : The BidList with values to be copied
     */
    public BidListDto(BidList bidList) {
        this.BidListId = bidList.getBidListId();
        this.account = bidList.getAccount();
        this.type = bidList.getType();
        this.bidQuantity = bidList.getBidQuantity();
    }

}
