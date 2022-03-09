package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.service.RatingService;
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
@WebMvcTest(RatingController.class)
@ActiveProfiles("test")
@WithMockUser
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    private RatingDto ratingDto;
    private List<RatingDto> ratingDtos;

    @Before
    public void setUp() {
        ratingDto = new RatingDto(1, "MR", "SPR", "FR", 1);

        RatingDto ratingDto2 = new RatingDto(2, "MR2", "SPR2", "FR2", 2);
        RatingDto ratingDto3 = new RatingDto(3, "MR3", "SPR3", "FR3", 3);
        ratingDtos = Arrays.asList(ratingDto, ratingDto2, ratingDto3);
    }

    @Test
    public void home() throws Exception {
        Mockito.when(ratingService.getAll()).thenReturn(ratingDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratingDto", hasSize(3)))
                .andExpect(model().attribute("ratingDto", hasItem(
                        allOf(
                                hasProperty("moodysRating", is("MR2")),
                                hasProperty("sandPRating", is("SPR2")),
                                hasProperty("fitchRating", is("FR2")),
                                hasProperty("orderNumber", is(2))
                        )
                )));
    }

    @Test
    public void addRatingForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void validate() throws Exception {
        Mockito.doNothing().when(Mockito.spy(ratingService)).save(ratingDto);
        Mockito.when(ratingService.getAll()).thenReturn(ratingDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingDto.getOrderNumber()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .param("moodysRating", "")
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingDto.getOrderNumber()))
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/add"));

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("orderNumber", String.valueOf(0))
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void showUpdateForm() throws Exception {
        Mockito.when(ratingService.getById(ArgumentMatchers.anyInt())).thenReturn(ratingDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("ratingDto"));
    }

    @Test
    public void updateRating() throws Exception {
        Mockito.doNothing().when(Mockito.spy(ratingService)).update(ratingDto);
        Mockito.when(ratingService.getAll()).thenReturn(ratingDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", 1)
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingDto.getOrderNumber()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", 1)
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void deleteRating() throws Exception {
        Mockito.doNothing().when(Mockito.spy(ratingService)).delete(ArgumentMatchers.anyInt());
        Mockito.when(ratingService.getAll()).thenReturn(ratingDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
    }
}