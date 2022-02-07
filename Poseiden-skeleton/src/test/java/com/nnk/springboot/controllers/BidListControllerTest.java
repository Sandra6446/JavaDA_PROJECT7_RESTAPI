package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.service.BidListService;
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
@WebMvcTest(BidListController.class)
@AutoConfigureMockMvc(secure = false)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    private BidListDto bidListDto;
    private List<BidListDto> bidListDtos;

    @Before
    public void setUp() {

        bidListDto = new BidListDto(1, "Account name", "type", 5d);

        BidListDto bidListDto1 = new BidListDto(1, "First bidList", "Type 1", 20.0);
        BidListDto bidListDto2 = new BidListDto(2, "Second bidList", "Type 2", 10.0);
        BidListDto bidListDto3 = new BidListDto(3, "Third bidList", "Type 3", 5.0);
        bidListDtos = Arrays.asList(bidListDto1, bidListDto2, bidListDto3);
    }

    @Test
    public void home() throws Exception {
        Mockito.when(bidListService.getAll()).thenReturn(bidListDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidListDto", hasSize(3)))
                .andExpect(model().attribute("bidListDto", hasItem(
                        allOf(
                                hasProperty("account", is("First bidList")),
                                hasProperty("type", is("Type 1")),
                                hasProperty("bidQuantity", is(20.0))
                        )
                )));
    }

    @Test
    public void addBidForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void validate() throws Exception {
        Mockito.doNothing().when(Mockito.spy(bidListService)).save(bidListDto);
        Mockito.when(bidListService.getAll()).thenReturn(bidListDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .param("account", bidListDto.getAccount())
                        .param("type", bidListDto.getType())
                        .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .param("account", "")
                        .param("type", bidListDto.getType())
                        .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/add"));

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .param("account", bidListDto.getAccount())
                        .param("type", bidListDto.getType())
                        .param("bidQuantity", String.valueOf(-20)))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void showUpdateForm() throws Exception {
        Mockito.when(bidListService.getById(ArgumentMatchers.anyInt())).thenReturn(bidListDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidListDto"));
    }

    @Test
    public void updateBid() throws Exception {
        Mockito.doNothing().when(Mockito.spy(bidListService)).update(bidListDto);
        Mockito.when(bidListService.getAll()).thenReturn(bidListDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/{id}", 1)
                        .param("account", bidListDto.getAccount())
                        .param("type", bidListDto.getType())
                        .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/{id}", 1)
                        .param("account", "")
                        .param("type", bidListDto.getType())
                        .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    public void deleteBid() throws Exception {
        Mockito.doNothing().when(Mockito.spy(bidListService)).delete(ArgumentMatchers.anyInt());
        Mockito.when(bidListService.getAll()).thenReturn(bidListDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/{id}",1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }
}