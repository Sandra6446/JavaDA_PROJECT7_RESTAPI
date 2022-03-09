package com.nnk.springboot.config.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomAuthenticationSuccessHandlerTest {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @MockBean
    private Authentication authentication;

    @Test
    public void withAdminAuthority_determineTargetUrl() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        Assert.assertEquals("/user/list",customAuthenticationSuccessHandler.determineTargetUrl(authentication));
    }

    @Test
    public void withUserAuthority_determineTargetUrl() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        Assert.assertEquals("/admin/home",customAuthenticationSuccessHandler.determineTargetUrl(authentication));
    }
}