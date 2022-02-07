package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.domain.entity.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TradeService implements CrudService <TradeDto> {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<TradeDto> getAll() {
        List<Trade> trades = tradeRepository.findAll();
        List<TradeDto> tradeDtos = new ArrayList<>();
        for (Trade trade : trades) {
            tradeDtos.add(new TradeDto(trade));
        }
        return tradeDtos;
    }

    @Override
    public TradeDto getById(Integer id) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        return new TradeDto(trade);
    }

    @Override
    public void save(TradeDto tradeDto) {
        tradeRepository.saveAndFlush(new Trade(tradeDto));
    }

    @Override
    public void update(TradeDto tradeDto) {
        Trade trade = tradeRepository.findById(tradeDto.getTradeId()).orElseThrow(() -> new IllegalArgumentException("Invalid trade with id :" + tradeDto.getTradeId()));
        trade.updateFromDto(tradeDto);
        tradeRepository.saveAndFlush(trade);
    }

    @Override
    public void delete(Integer id) {
        tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade with id :" + id));
        tradeRepository.deleteById(id);
    }
}
