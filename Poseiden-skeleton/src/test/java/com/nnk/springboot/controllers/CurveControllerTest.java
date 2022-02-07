package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CurveController.class)
@AutoConfigureMockMvc(secure = false)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    private CurvePointDto curvePointDto;
    private List<CurvePointDto> curvePointDtos;

    @Before
    public void setUp() {
        curvePointDto = new CurvePointDto(4, 5, 10d, 5d);

        CurvePointDto curvePointDto1 = new CurvePointDto(1, 12, 20d, 20d);
        CurvePointDto curvePointDto2 = new CurvePointDto(2, 10, 10d, 5d);
        CurvePointDto curvePointDto3 = new CurvePointDto(3, 3, 10d, 10d);
        curvePointDtos = Arrays.asList(curvePointDto1, curvePointDto2, curvePointDto3);
    }

    @Test
    public void home() throws Exception {
        Mockito.when(curvePointService.getAll()).thenReturn(curvePointDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePointDto", hasSize(3)))
                .andExpect(model().attribute("curvePointDto", hasItem(
                        allOf(
                                hasProperty("curveId", is(12)),
                                hasProperty("term", is(20d)),
                                hasProperty("value", is(20d))
                        )
                )));
    }

    @Test
    public void addCurveForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void validate() throws Exception {
        Mockito.doNothing().when(Mockito.spy(curvePointService)).save(curvePointDto);
        Mockito.when(curvePointService.getAll()).thenReturn(curvePointDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", String.valueOf(curvePointDto.getValue())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .param("curveId", String.valueOf(-20))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", String.valueOf(curvePointDto.getValue())))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/add"));

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", ""))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void showUpdateForm() throws Exception {
        Mockito.when(curvePointService.getById(ArgumentMatchers.anyInt())).thenReturn(curvePointDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePointDto"));
    }

    @Test
    public void updateBid() throws Exception {
        Mockito.doNothing().when(Mockito.spy(curvePointService)).update(curvePointDto);
        Mockito.when(curvePointService.getAll()).thenReturn(curvePointDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/{id}", 1)
                        .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", String.valueOf(curvePointDto.getValue())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/{id}", 1)
                        .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                        .param("term", String.valueOf(curvePointDto.getTerm()))
                        .param("value", ""))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void deleteBid() throws Exception {
        Mockito.doNothing().when(Mockito.spy(curvePointService)).delete(ArgumentMatchers.anyInt());
        Mockito.when(curvePointService.getAll()).thenReturn(curvePointDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/{id}",1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }
}