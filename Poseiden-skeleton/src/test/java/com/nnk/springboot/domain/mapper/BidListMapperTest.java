package com.nnk.springboot.domain.mapper;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import org.junit.Assert;
import org.junit.Test;

public class BidListMapperTest {

    private final BidList bidList = new BidList("Account name", "Type", 20.5);
    private final BidListDto bidListDto = new BidListDto(bidList.getBidListId(), "Account name", "Type", 20.5);

    @Test
    public void toBidListDto() {
        Assert.assertEquals(bidListDto,BidListMapper.INSTANCE.toBidListDto(bidList));
    }

    @Test
    public void toBidList() {
        Assert.assertEquals(bidList,BidListMapper.INSTANCE.toBidList(bidListDto));
    }

    @Test
    public void testToBidList() {
        BidList anotherBidList = new BidList("Account name", "Type", 20.5);
        anotherBidList.setCommentary("pour test");
        Assert.assertEquals(anotherBidList,BidListMapper.INSTANCE.toBidList(bidListDto,anotherBidList));
    }
}