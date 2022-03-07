package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.BidListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BidListTest {

    @Test
    public void updateFromDto() {
        BidListDto bidListDto = new BidListDto(1,"Account new name","New type", 10d);
        BidList bidList = new BidList(1,"Account name","Type",5d);
        BidList updatedBidList = new BidList(1,"Account new name","New type", 10d);

        bidList.updateFromDto(bidListDto);
        Assert.assertEquals(updatedBidList,bidList);

        // BidListDto with bad id
        bidListDto.setBidListId(2);
        bidListDto.setBidQuantity(5d);
        bidList.updateFromDto(bidListDto);
        Assert.assertEquals(updatedBidList,bidList);
    }
}