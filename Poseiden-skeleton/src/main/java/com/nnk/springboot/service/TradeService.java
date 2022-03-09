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

/**
 * Manages the Trade data
 */
@Service
@Transactional
@AllArgsConstructor
public class TradeService implements CrudService<TradeDto> {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Gets all Trades in database
     *
     * @return A list of corresponding TradeDto
     */
    @Override
    public List<TradeDto> getAll() {
        List<Trade> trades = tradeRepository.findAll();
        List<TradeDto> tradeDtos = new ArrayList<>();
        for (Trade trade : trades) {
            tradeDtos.add(new TradeDto(trade));
        }
        return tradeDtos;
    }

    /**
     * Gets a specific Trade in database
     *
     * @param id : The id to be found
     * @return The corresponding TradeDto
     */
    @Override
    public TradeDto getById(Integer id) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        return new TradeDto(trade);
    }

    /**
     * Saves a Trade in database
     *
     * @param tradeDto : The TradeDto with values to be saved
     */
    @Override
    public void save(TradeDto tradeDto) {
        tradeRepository.saveAndFlush(new Trade(tradeDto));
    }

    /**
     * Updates a Trade in database
     *
     * @param tradeDto : The TradeDto with new values
     */
    @Override
    public void update(TradeDto tradeDto) {
        Trade trade = tradeRepository.findById(tradeDto.getTradeId()).orElseThrow(() -> new IllegalArgumentException("Invalid trade with id :" + tradeDto.getTradeId()));
        trade.updateFromDto(tradeDto);
        tradeRepository.saveAndFlush(trade);
    }

    /**
     * Deletes a Trade in database
     *
     * @param id : The id of the Trade to be deleted
     */
    @Override
    public void delete(Integer id) {
        tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade with id :" + id));
        tradeRepository.deleteById(id);
    }
}
