package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.domain.entity.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BidListServiceTest {

    private BidListService bidListService;

    @MockBean
    private BidListRepository bidListRepository;

    private List<BidList> bidLists;
    private BidList bidList;
    private List<BidListDto> bidListDtos;
    private BidListDto bidListDto;

    @Before
    public void setUp() {
        bidList = new BidList(1, "First bidList", "Type 1", 20d);
        BidList bidList2 = new BidList(2, "Second bidList", "Type 2", 10d);
        BidList bidList3 = new BidList(3, "Third bidList", "Type 3", 5d);
        bidLists = Arrays.asList(bidList, bidList2, bidList3);

        bidListDto = new BidListDto(bidList);
        BidListDto bidListDto2 = new BidListDto(bidList2);
        BidListDto bidListDto3 = new BidListDto(bidList3);
        bidListDtos = Arrays.asList(bidListDto, bidListDto2, bidListDto3);

        bidListService = new BidListService(bidListRepository);
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
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(bidList));
        Assert.assertEquals(bidListDto, bidListService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        bidListService.getById(1);
    }

    @Test
    public void save() {
        // La base est vide pour le test donc id=1
        BidList bidListToSave = new BidList(1, "First bidList", "Type 1", 20d);
        bidListService.save(bidListDto);
        verify(bidListRepository, times(1)).saveAndFlush(bidListToSave);
    }

    @Test
    public void update() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(bidList));
        BidListDto bidListDtoWithNewValues = new BidListDto(1, "New bidList", "Type 1", 20d);
        bidListService.update(bidListDtoWithNewValues);
        BidList bidListToUpdate = new BidList(1, "New bidList", "Type 1", 20d);
        verify(bidListRepository, times(1)).saveAndFlush(bidListToUpdate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        bidListService.update(bidListDto);
    }

    @Test
    public void delete() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(bidList));
        bidListService.delete(1);
        verify(bidListRepository, times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(bidListRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        bidListService.delete(1);
    }
}