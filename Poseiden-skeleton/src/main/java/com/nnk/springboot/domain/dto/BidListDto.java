package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.BidList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidListDto {

    // TODO : Faire un tour des champs obligatoires

    private Integer BidListId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @PositiveOrZero(message = "Value must be greater than or equal to zero")
    private Double bidQuantity;

    public BidListDto(BidList bidList) {
        this.BidListId = bidList.getBidListId();
        this.account = bidList.getAccount();
        this.type = bidList.getType();
        this.bidQuantity = bidList.getBidQuantity();
    }

}
