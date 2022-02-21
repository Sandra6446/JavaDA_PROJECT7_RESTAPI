package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.TradeDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TradeTest {

    @Test
    public void updateFromDto() {
        TradeDto tradeDto = new TradeDto(1,"Account new name","New type", 10.0);
        Trade trade = new Trade(1,"Account name","Type",5);
        Trade updatedTrade = new Trade(1,"Account new name","New type", 10.0);

        trade.updateFromDto(tradeDto);
        Assert.assertEquals(updatedTrade,trade);

        // TradeDto with bad id
        tradeDto.setTradeId(2);
        tradeDto.setBuyQuantity(5.0);
        trade.updateFromDto(tradeDto);
        Assert.assertEquals(updatedTrade,trade);
    }
}