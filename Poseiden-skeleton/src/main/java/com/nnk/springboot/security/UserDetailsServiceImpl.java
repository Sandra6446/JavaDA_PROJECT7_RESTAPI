package com.nnk.springboot.security;

import com.nnk.springboot.domain.entity.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Authentication service
 */
@AllArgsConstructor
@NoArgsConstructor
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    /**
     * Gets a user by username
     *
     * @param username : The username to be found in database
     * @return A User if the operation succeed, otherwise an exception with the reason of the failure
     *
     * @see User
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
        if (!userEntityOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        String authority = String.format("ROLE_%s",userEntityOptional.get().getRole());
        return new User(userEntityOptional.get().getUsername(), userEntityOptional.get().getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(authority)));
    }

    /**
     * Encodes the user password
     *
     * @param password : The password to be encoded
     * @return The encoded password
     */
    public String encode(String password) {
        return bcryptEncoder.encode(password);
    }
}
