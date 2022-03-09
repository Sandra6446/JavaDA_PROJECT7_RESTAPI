package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.UserDto;
import com.nnk.springboot.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(authorities = {"ROLE_ADMIN"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto;
    private List<UserDto> userDtos;

    @Before
    public void setUp() {

        userDto = new UserDto(1, "username", "P@ssw0rd", "fullname", "USER");

        UserDto userDto2 = new UserDto(2, "username2", "password2", "fullname2", "ADMIN");
        UserDto userDto3 = new UserDto(3, "username3", "password3", "fullname3", "USER");
        userDtos = Arrays.asList(userDto, userDto2, userDto3);
    }

    @Test
    public void withAdminAuthority_home() throws Exception {
        Mockito.when(userService.getAll()).thenReturn(userDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", hasSize(3)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("username", is("username2")),
                                hasProperty("fullname", is("fullname2")),
                                hasProperty("role", is("ADMIN"))
                        )
                )));
    }

    @Test
    @WithMockUser
    public void withUserAuthority_home() throws Exception {
        Mockito.when(userService.getAll()).thenReturn(userDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void withAdminAuthority_addUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser
    public void withUserAuthority_addUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void withAdminAuthority_validate() throws Exception {
        Mockito.doNothing().when(Mockito.spy(userService)).save(userDto);
        Mockito.when(userService.getAll()).thenReturn(userDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .param("fullname", userDto.getFullname())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("role", userDto.getRole())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .param("username", userDto.getUsername())
                        .param("password", "password")
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .param("username", userDto.getUsername())
                        .param("password", "P@ssword")
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .param("username", userDto.getUsername())
                        .param("password", "Passw0rd")
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser
    public void withUserAuthority_validate() throws Exception {
        Mockito.doNothing().when(Mockito.spy(userService)).save(userDto);
        Mockito.when(userService.getAll()).thenReturn(userDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole())
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void showUpdateForm() throws Exception {
        Mockito.when(userService.getById(ArgumentMatchers.anyInt())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("userDto"));
    }

    @Test
    public void withAdminAuthority_updateUser() throws Exception {
        Mockito.doNothing().when(Mockito.spy(userService)).update(userDto);
        Mockito.when(userService.getAll()).thenReturn(userDtos);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", 1)
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", 1)
                        .param("username", userDto.getUsername())
                        .param("password", "password")
                        .param("fullname", userDto.getFullname())
                        .param("role", userDto.getRole())
                        .with(csrf()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void deleteUser() throws Exception {
        Mockito.doNothing().when(Mockito.spy(userService)).delete(ArgumentMatchers.anyInt());
        Mockito.when(userService.getAll()).thenReturn(userDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }
}