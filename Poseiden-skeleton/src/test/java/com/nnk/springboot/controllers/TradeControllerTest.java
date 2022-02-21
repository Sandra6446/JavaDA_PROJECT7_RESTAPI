package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.service.TradeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    private TradeDto tradeDto;
    private List<TradeDto> tradeDtos;

    @Before
    public void setUp() {

        tradeDto = new TradeDto(1, "Account name", "type", 5d);

        TradeDto tradeDto2 = new TradeDto(2, "Second trade", "Type 2", 10d);
        TradeDto tradeDto3 = new TradeDto(3, "Third trade", "Type 3", 5d);
        tradeDtos = Arrays.asList(tradeDto, tradeDto2, tradeDto3);
    }

    @Test
    public void home() throws Exception {
        Mockito.when(tradeService.getAll()).thenReturn(tradeDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("tradeDto", hasSize(3)))
                .andExpect(model().attribute("tradeDto", hasItem(
                        allOf(
                                hasProperty("account", is("Second trade")),
                                hasProperty("type", is("Type 2")),
                                hasProperty("buyQuantity", is(10d))
                        )
                )));
    }

    @Test
    public void addBidForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void validate() throws Exception {
        Mockito.doNothing().when(Mockito.spy(tradeService)).save(tradeDto);
        Mockito.when(tradeService.getAll()).thenReturn(tradeDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .param("account", "")
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/add"));

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(-20)))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void showUpdateForm() throws Exception {
        Mockito.when(tradeService.getById(ArgumentMatchers.anyInt())).thenReturn(tradeDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("tradeDto"));
    }

    @Test
    public void updateBid() throws Exception {
        Mockito.doNothing().when(Mockito.spy(tradeService)).update(tradeDto);
        Mockito.when(tradeService.getAll()).thenReturn(tradeDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/{id}", 1)
                        .param("account", tradeDto.getAccount())
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/{id}", 1)
                        .param("account", "")
                        .param("type", tradeDto.getType())
                        .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void deleteBid() throws Exception {
        Mockito.doNothing().when(Mockito.spy(tradeService)).delete(ArgumentMatchers.anyInt());
        Mockito.when(tradeService.getAll()).thenReturn(tradeDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/{id}",1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }
}