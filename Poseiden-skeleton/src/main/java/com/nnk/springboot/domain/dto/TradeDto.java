package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeDto {

    private Integer TradeId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotNull(message = "Bid quantity is mandatory")
    @Positive(message = "Bid quantity must be positive")
    private Double buyQuantity;

    public TradeDto(Trade trade) {
        this.TradeId = trade.getTradeId();
        this.account = trade.getAccount();
        this.type = trade.getType();
        this.buyQuantity = trade.getBuyQuantity();
    }
}
