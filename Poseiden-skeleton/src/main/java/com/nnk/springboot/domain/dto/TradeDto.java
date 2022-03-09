package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * Represents a trade
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeDto {

    private Integer TradeId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @PositiveOrZero(message = "Value must be greater than or equal to zero")
    private Double buyQuantity;

    /**
     * Build a TradeDto with Trade values
     *
     * @param trade : The Trade with values to be copied
     */
    public TradeDto(Trade trade) {
        this.TradeId = trade.getTradeId();
        this.account = trade.getAccount();
        this.type = trade.getType();
        this.buyQuantity = trade.getBuyQuantity();
    }
}
