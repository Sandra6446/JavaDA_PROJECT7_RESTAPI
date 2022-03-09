package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.domain.entity.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the RuleName data
 */
@Service
@Transactional
@AllArgsConstructor
public class RuleNameService implements CrudService<RuleNameDto> {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Gets all RuleNames in database
     *
     * @return A list of corresponding RuleNameDto
     */
    @Override
    public List<RuleNameDto> getAll() {
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        List<RuleNameDto> ruleNameDtos = new ArrayList<>();
        for (RuleName ruleName : ruleNames) {
            ruleNameDtos.add(new RuleNameDto(ruleName));
        }
        return ruleNameDtos;
    }

    /**
     * Gets a specific RuleName in database
     *
     * @param id : The id to be found
     * @return The corresponding RuleNameDto
     */
    @Override
    public RuleNameDto getById(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        return new RuleNameDto(ruleName);
    }

    /**
     * Saves a RuleName in database
     *
     * @param ruleNameDto : The RuleNameDto with values to be saved
     */
    @Override
    public void save(RuleNameDto ruleNameDto) {
        ruleNameRepository.saveAndFlush(new RuleName(ruleNameDto));
    }

    /**
     * Updates a RuleName in database
     *
     * @param ruleNameDto : The RuleNameDto with new values
     */
    @Override
    public void update(RuleNameDto ruleNameDto) {
        RuleName ruleName = ruleNameRepository.findById(ruleNameDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid rule with id :" + ruleNameDto.getId()));
        ruleName.updateFromDto(ruleNameDto);
        ruleNameRepository.saveAndFlush(ruleName);
    }

    /**
     * Deletes a RuleName in database
     *
     * @param id : The id of the RuleName to be deleted
     */
    @Override
    public void delete(Integer id) {
        ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rule with id :" + id));
        ruleNameRepository.deleteById(id);
    }
}
