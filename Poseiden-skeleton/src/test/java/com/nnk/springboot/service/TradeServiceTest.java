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
    private Trade trade;
    private List<TradeDto> tradeDtos;
    private TradeDto tradeDto;

    @Before
    public void setUp() {
        trade = new Trade(1, "First trade", "Type 1", 20.0);
        Trade trade2 = new Trade(2, "Second trade", "Type 2", 10.0);
        Trade trade3 = new Trade(3, "Third trade", "Type 3", 5.0);
        trades = Arrays.asList(trade, trade2, trade3);

        tradeDto = new TradeDto(trade);
        TradeDto tradeDto2 = new TradeDto(trade2);
        TradeDto tradeDto3 = new TradeDto(trade3);
        tradeDtos = Arrays.asList(tradeDto, tradeDto2, tradeDto3);

        tradeService = new TradeService(tradeRepository);
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
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(trade));
        Assert.assertEquals(tradeDto, tradeService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        tradeService.getById(1);
    }

    @Test
    public void save() {
        // La base est vide pour le test donc id=1
        Trade tradeToSave = new Trade(1, "First trade", "Type 1", 20.0);
        tradeService.save(tradeDto);
        verify(tradeRepository, times(1)).saveAndFlush(tradeToSave);
    }

    @Test
    public void withNullValue_save() {
        Trade tradeToSave = new Trade(1, "First trade", "Type 1", 0d);
        TradeDto tradeDtoWithNullValues = new TradeDto(1, "First trade", "Type 1", null);
        tradeService.save(tradeDtoWithNullValues);
        verify(tradeRepository, times(1)).saveAndFlush(tradeToSave);
    }

    @Test
    public void update() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(trade));
        TradeDto tradeDtoWithNewValues = new TradeDto(1, "New trade", "Type 1", 20.0);
        tradeService.update(tradeDtoWithNewValues);
        Trade tradeUpdated = new Trade(1, "New trade", "Type 1", 20.0);
        verify(tradeRepository, times(1)).saveAndFlush(tradeUpdated);
    }

    @Test
    public void withNullValues_update() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(trade));
        TradeDto tradeDtoWithNullValues = new TradeDto(1, "New trade", "Type 1", null);
        tradeService.update(tradeDtoWithNullValues);
        Trade tradeUpdated = new Trade(1, "New trade", "Type 1", 0d);
        verify(tradeRepository, times(1)).saveAndFlush(tradeUpdated);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        tradeService.update(tradeDto);
    }

    @Test
    public void delete() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(trade));
        tradeService.delete(1);
        verify(tradeRepository, times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(tradeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        tradeService.delete(1);
    }
}