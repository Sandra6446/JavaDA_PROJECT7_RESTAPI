package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.domain.entity.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeServiceTest {

    private TradeService tradeService;

    @MockBean
    private TradeRepository tradeRepository;

    private List<Trade> trades;
    private List<TradeDto> tradeDtos;

    @Before
    public void setUp() {
        Trade trade1 = new Trade(1, "First trade", "Type 1", 20.0);
        Trade trade2 = new Trade(2, "Second trade", "Type 2", 10.0);
        Trade trade3 = new Trade(3, "Third trade", "Type 3", 5.0);
        trades = Arrays.asList(trade1, trade2, trade3);
        tradeService = new TradeService(tradeRepository);

        TradeDto tradeDto1 = new TradeDto(1, "First trade", "Type 1", 20.0);
        TradeDto tradeDto2 = new TradeDto(2, "Second trade", "Type 2", 10.0);
        TradeDto tradeDto3 = new TradeDto(3, "Third trade", "Type 3", 5.0);
        tradeDtos = Arrays.asList(tradeDto1, tradeDto2, tradeDto3);
    }

    @Test
    public void getAll() {
        Mockito.when(tradeRepository.findAll()).thenReturn(trades);
        Assert.assertEquals(tradeDtos, tradeService.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getAll() {
        Mockito.when(tradeRepository.findAll()).thenThrow(new IllegalArgumentException());
        tradeService.getAll();
    }

    @Test
    public void getById() {
        TradeDto tradeDto = new TradeDto(1, "First trade", "Type 1", 20.0);
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(trades.get(0)));
        Assert.assertEquals(tradeDto, tradeService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        tradeService.getById(1);
    }

    @Test
    public void save() {
        TradeDto tradeDto = tradeDtos.get(0);
        Trade trade = trades.get(0);
        tradeService.save(tradeDto);
        verify(tradeRepository,times(1)).saveAndFlush(trade);
    }

    @Test
    public void update() {
        TradeDto tradeDto = tradeDtos.get(0);
        Trade trade = trades.get(0);
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(trade));
        tradeService.update(tradeDto);
        verify(tradeRepository,times(1)).saveAndFlush(trade);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        tradeService.update(tradeDtos.get(0));
    }

    @Test
    public void delete() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(trades.get(0)));
        tradeService.delete(1);
        verify(tradeRepository,times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        tradeService.delete(1);
    }
}