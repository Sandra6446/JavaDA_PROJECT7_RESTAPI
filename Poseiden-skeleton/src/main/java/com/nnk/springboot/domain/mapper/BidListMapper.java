package com.nnk.springboot.domain.mapper;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BidListMapper {

        BidListMapper INSTANCE = Mappers.getMapper(BidListMapper.class);

        BidListDto toBidListDto(BidList bidList);
        BidList toBidList(BidListDto bidListDto, @MappingTarget BidList bidList);

        BidList toBidList(BidListDto bidListDto);
}
