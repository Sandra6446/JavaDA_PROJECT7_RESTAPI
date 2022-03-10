package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.domain.entity.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameServiceTest {

    private RuleNameService ruleNameService;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    private List<RuleName> ruleNames;
    private RuleName ruleName;
    private List<RuleNameDto> ruleNamesDto;
    private RuleNameDto ruleNameDto;

    @Before
    public void setUp() throws Exception {
        ruleName = new RuleName(1, "Name1", "Description1", "Json", "Template", "SqlStr", "SqlPart");
        RuleName ruleName2 = new RuleName(2, "Name2", "Description2", "Json", "Template", "SqlStr", "SqlPart");
        RuleName ruleName3 = new RuleName(3, "Name3", "Description3", "Json", "Template", "SqlStr", "SqlPart");
        ruleNames = Arrays.asList(ruleName, ruleName2, ruleName3);

        ruleNameDto = new RuleNameDto(ruleName);
        RuleNameDto ruleNameDto2 = new RuleNameDto(ruleName2);
        RuleNameDto ruleNameDto3 = new RuleNameDto(ruleName3);
        ruleNamesDto = Arrays.asList(ruleNameDto, ruleNameDto2, ruleNameDto3);

        ruleNameService = new RuleNameService(ruleNameRepository);
    }

    @Test
    public void getAll() {
        Mockito.when(ruleNameRepository.findAll()).thenReturn(ruleNames);
        Assert.assertEquals(ruleNamesDto, ruleNameService.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getAll() {
        Mockito.when(ruleNameRepository.findAll()).thenThrow(new IllegalArgumentException());
        ruleNameService.getAll();
    }

    @Test
    public void getById() {
        Mockito.when(ruleNameRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(ruleName));
        Assert.assertEquals(ruleNameDto, ruleNameService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(ruleNameRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        ruleNameService.getById(1);
    }

    @Test
    public void save() {
        // La base est vide pour le test donc id=1
        RuleName ruleNameToSave = new RuleName(1, "Name1", "Description1", "Json", "Template", "SqlStr", "SqlPart");
        ruleNameService.save(ruleNameDto);
        verify(ruleNameRepository, times(1)).saveAndFlush(ruleNameToSave);
    }

    @Test
    public void update() {
        Mockito.when(ruleNameRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(ruleName));
        RuleNameDto ruleNameDtoWithNewValues = new RuleNameDto(1, "New name", "Description1", "Json", "Template", "SqlStr", "SqlPart");
        ruleNameService.update(ruleNameDtoWithNewValues);
        RuleName ruleNameToUpdate = new RuleName(1, "New name", "Description1", "Json", "Template", "SqlStr", "SqlPart");
        verify(ruleNameRepository, times(1)).saveAndFlush(ruleNameToUpdate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_update() {
        Mockito.when(ruleNameRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        ruleNameService.update(ruleNameDto);
    }

    @Test
    public void delete() {
        Mockito.when(ruleNameRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(ruleName));
        ruleNameService.delete(1);
        verify(ruleNameRepository, times(1)).deleteById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_delete() {
        Mockito.when(ruleNameRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        ruleNameService.delete(1);
    }
}