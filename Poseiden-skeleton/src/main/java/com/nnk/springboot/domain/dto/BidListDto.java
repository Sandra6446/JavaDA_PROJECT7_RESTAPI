package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.BidList;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
    @NotNull(message = "Bid quantity is mandatory")
    @Positive(message = "Bid quantity must be positive")
    private Double bidQuantity;

    public BidListDto(BidList bidList) {
        this.BidListId = bidList.getBidListId();
        this.account = bidList.getAccount();
        this.type = bidList.getType();
        this.bidQuantity = bidList.getBidQuantity();
    }

}
