package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.domain.entity.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BidListService implements CrudService<BidListDto> {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidListDto> getAll() {
        List<BidList> bidLists = bidListRepository.findAll();
        List<BidListDto> bidListDtos = new ArrayList<>();
        for (BidList bidList : bidLists) {
            bidListDtos.add(new BidListDto(bidList));
        }
        return bidListDtos;
    }

    @Override
    public BidListDto getById(Integer id) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        return new BidListDto(bidList);
    }

    @Override
    public void save(BidListDto bidListDto) {
        bidListRepository.saveAndFlush(new BidList(bidListDto));
    }

    @Override
    public void update(BidListDto bidListDto) {
        BidList bidList = bidListRepository.findById(bidListDto.getBidListId()).orElseThrow(() -> new IllegalArgumentException("Invalid bid with id :" + bidListDto.getBidListId()));
        bidList.updateFromDto(bidListDto);
        bidListRepository.saveAndFlush(bidList);
    }

    @Override
    public void delete(Integer id) {
        bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid with id :" + id));
        bidListRepository.deleteById(id);
    }
}
