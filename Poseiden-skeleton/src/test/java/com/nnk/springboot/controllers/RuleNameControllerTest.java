package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.service.RuleNameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RuleNameController.class)
@ActiveProfiles("test")
@WithMockUser
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    private RuleNameDto ruleNameDto;
    private List<RuleNameDto> ruleNameDtos;

    @Before
    public void setUp() {
        ruleNameDto = new RuleNameDto(1, "Name", "Description", "Json", "Template", "SqlStr", "SqlPart");

        RuleNameDto ruleNameDto2 = new RuleNameDto(2, "Name2", "Description2", "Json", "Template", "SqlStr", "SqlPart");
        RuleNameDto ruleNameDto3 = new RuleNameDto(3, "Name3", "Description3", "Json", "Template", "SqlStr", "SqlPart");
        ruleNameDtos = Arrays.asList(ruleNameDto, ruleNameDto2, ruleNameDto3);
    }

    @Test
    public void home() throws Exception {
        Mockito.when(ruleNameService.getAll()).thenReturn(ruleNameDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNameDto", hasSize(3)))
                .andExpect(model().attribute("ruleNameDto", hasItem(
                        allOf(
                                hasProperty("name", is("Name2")),
                                hasProperty("description", is("Description2")),
                                hasProperty("json", is("Json")),
                                hasProperty("template", is("Template")),
                                hasProperty("sqlStr", is("SqlStr")),
                                hasProperty("sqlPart", is("SqlPart"))
                        )
                )));
    }

    @Test
    public void addRuleForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void validate() throws Exception {
        Mockito.doNothing().when(Mockito.spy(ruleNameService)).save(ruleNameDto);
        Mockito.when(ruleNameService.getAll()).thenReturn(ruleNameDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .param("name", ruleNameDto.getName())
                        .param("description", ruleNameDto.getDescription())
                        .param("json", ruleNameDto.getJson())
                        .param("template", ruleNameDto.getTemplate())
                        .param("sqlStr", ruleNameDto.getSqlStr())
                        .param("sqlPart", ruleNameDto.getSqlPart())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .param("name", "")
                        .param("description", ruleNameDto.getDescription())
                        .param("json", ruleNameDto.getJson())
                        .param("template", ruleNameDto.getTemplate())
                        .param("sqlStr", ruleNameDto.getSqlStr())
                        .param("sqlPart", ruleNameDto.getSqlPart())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void showUpdateForm() throws Exception {
        Mockito.when(ruleNameService.getById(ArgumentMatchers.anyInt())).thenReturn(ruleNameDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleNameDto"));
    }

    @Test
    public void updateRuleName() throws Exception {
        Mockito.doNothing().when(Mockito.spy(ruleNameService)).update(ruleNameDto);
        Mockito.when(ruleNameService.getAll()).thenReturn(ruleNameDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/{id}", 1)
                        .param("name", ruleNameDto.getName())
                        .param("description", ruleNameDto.getDescription())
                        .param("json", ruleNameDto.getJson())
                        .param("template", ruleNameDto.getTemplate())
                        .param("sqlStr", ruleNameDto.getSqlStr())
                        .param("sqlPart", ruleNameDto.getSqlPart())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/{id}", 1)
                        .param("name", "")
                        .param("description", ruleNameDto.getDescription())
                        .param("json", ruleNameDto.getJson())
                        .param("template", ruleNameDto.getTemplate())
                        .param("sqlStr", ruleNameDto.getSqlStr())
                        .param("sqlPart", ruleNameDto.getSqlPart())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void deleteRuleName() throws Exception {
        Mockito.doNothing().when(Mockito.spy(ruleNameService)).delete(ArgumentMatchers.anyInt());
        Mockito.when(ruleNameService.getAll()).thenReturn(ruleNameDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
    }
}