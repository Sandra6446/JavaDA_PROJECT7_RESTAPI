package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.domain.mapper.BidListMapper;
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
public class BidListService implements CrudService <BidListDto> {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidListDto> getAll() {
        List<BidList> bidLists = bidListRepository.findAll();
        List<BidListDto> bidListDtos = new ArrayList<>();
        for (BidList bidList : bidLists) {
            bidListDtos.add(BidListMapper.INSTANCE.toBidListDto(bidList));
        }
        return bidListDtos;
    }

    @Override
    public BidListDto getById(Integer id) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        return BidListMapper.INSTANCE.toBidListDto(bidList);
    }

    @Override
    public void save(BidListDto bidListDto) {
        bidListRepository.saveAndFlush(BidListMapper.INSTANCE.toBidList(bidListDto));
    }

    @Override
    public void update(BidListDto bidListDto) {
        BidList bidListEntity = bidListRepository.findById(bidListDto.getBidListId()).orElseThrow(() -> new IllegalArgumentException("Invalid bid with id :" + bidListDto.getBidListId()));
        bidListRepository.saveAndFlush(BidListMapper.INSTANCE.toBidList(bidListDto, bidListEntity));
    }

    @Override
    public void delete(Integer id) {
        bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid with id :" + id));
        bidListRepository.deleteById(id);
    }
}
