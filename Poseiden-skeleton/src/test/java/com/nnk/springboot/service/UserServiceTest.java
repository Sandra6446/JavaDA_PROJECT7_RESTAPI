package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.UserDto;
import com.nnk.springboot.domain.entity.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;
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
public class UserServiceTest {

    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private List<UserEntity> userEntities;
    private UserEntity userEntity;
    private List<UserDto> userDtos;
    private UserDto userDto;

    @Before
    public void setUp() {
        userEntity = new UserEntity(1, "username","P@ssw0rd","fullname","USER");
        UserEntity userEntity2 = new UserEntity(2, "username2","password2","fullname2","ADMIN");
        UserEntity userEntity3 = new UserEntity(3, "username3","password3","fullname3","USER");
        userEntities = Arrays.asList(userEntity, userEntity2, userEntity3);

        userDto = new UserDto(userEntity);
        UserDto userDto2 = new UserDto(userEntity2);
        UserDto userDto3 = new UserDto(userEntity3);
        userDtos = Arrays.asList(userDto, userDto2, userDto3);

        userService = new UserService(userRepository,userDetailsService);
    }

    @Test
    public void getAll() {
        Mockito.when(userRepository.findAll()).thenReturn(userEntities);
        Assert.assertEquals(userDtos, userService.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getAll() {
        Mockito.when(userRepository.findAll()).thenThrow(new IllegalArgumentException());
        userService.getAll();
    }

    @Test
    public void getById() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(userEntity));
        Assert.assertEquals(userDto, userService.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExceptionThrown_getById() {
        Mockito.when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        userService.getById(1);
    }

    @Test
    public void save() {
        Mockito.when(userDetailsService.encode(ArgumentMatchers.anyString())).thenReturn("$2a$10$n.mMy8BUJ2D87A.1bRpKzOCCTBvbv2XTKtlT6RD/q6Uas/gpZYbkG");
        UserEntity userEntityToSave = new UserEntity( "username","$2a$10$n.mMy8BUJ2D87A.1bRpKzOCCTBvbv2XTKtlT6RD/q6Uas/gpZYbkG","fullname","USER");
        userService.save(userDto);
        verify(userRepository,times(1)).saveAndFlush(userEntityToSave);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}