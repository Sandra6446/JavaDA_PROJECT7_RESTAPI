package com.nnk.springboot.security;

import com.nnk.springboot.domain.entity.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserEntity userEntity;

    @Before
    public void setUp() {

        userDetailsService = new UserDetailsServiceImpl(userRepository,passwordEncoder);

        userEntity = new UserEntity(1, "username","P@ssw0rd","fullname","USER");

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        user = new User("username","P@ssw0rd",authorities);

    }

    @Test
    public void loadUserByUsername() {
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Assert.assertEquals(user,userDetailsService.loadUserByUsername("username"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenExceptionThrown_loadUserByUsername() {
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        userDetailsService.loadUserByUsername("username");
    }
}