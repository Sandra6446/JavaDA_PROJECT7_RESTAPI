package com.nnk.springboot.service;

import com.nnk.springboot.domain.entity.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListServiceTest {

    private BidListService bidListService;

    @MockBean
    private BidListRepository bidListRepository;

    private List<BidList> bidLists;
    private List<BidListDto> bidListDtos;

    @Before
    public void setUp() {
        BidList bidList1 = new BidList(1, "First bidList", "Type 1", 20.0);
        BidList bidList2 = new BidList(2, "Second bidList", "Type 2", 10.0);
        BidList bidList3 = new BidList(3, "Third bidList", "Type 3", 5.0);
        bidLists = Arrays.asList(bidList1, bidList2, bidList3);
        bidListService = new BidListService(bidListRepository);

        BidListDto bidListDto1 = new BidListDto(1, "First bidList", "Type 1", 20.0);
        BidListDto bidListDto2 = new BidListDto(2, "Second bidList", "Type 2", 10.0);
        BidListDto bidListDto3 = new BidListDto(3, "Third bidList", "Type 3", 5.0);
        bidListDtos = Arrays.asList(bidListDto1, bidListDto2, bidListDto3);
    }

    @Test
    public void getAll() {
        Mockito.when(bidListRepository.findAll()).thenReturn(bidLists);
        Assert.assertEquals(bidListDtos, bidListService.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getAll() {
        Mockito.when(bidListRepository.findAll()).thenThrow(new IllegalArgumentException());
        bidListService.getAll();
    }

    @Test
    public void getById() {
        BidListDto bidListDto = new BidListDto(1, "First bidList", "Type 1", 20.0);
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(bidLists.get(0)));
        Assert.assertEquals(bidListDto, bidListService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        bidListService.getById(1);
    }

    @Test
    public void save() {
        BidListDto bidListDto = bidListDtos.get(0);
        BidList bidList = bidLists.get(0);
        bidListService.save(bidListDto);
        verify(bidListRepository,times(1)).saveAndFlush(bidList);
    }

    @Test
    public void update() {
        BidListDto bidListDto = bidListDtos.get(0);
        BidList bidList = bidLists.get(0);
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(bidList));
        bidListService.update(bidListDto);
        verify(bidListRepository,times(1)).saveAndFlush(bidList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        bidListService.update(bidListDtos.get(0));
    }

    @Test
    public void delete() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(bidLists.get(0)));
        bidListService.delete(1);
        verify(bidListRepository,times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        bidListService.delete(1);
    }
}