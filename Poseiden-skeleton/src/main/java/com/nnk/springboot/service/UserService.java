package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.UserDto;
import com.nnk.springboot.domain.entity.Trade;
import com.nnk.springboot.domain.entity.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService implements CrudService<UserDto> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Override
    public List<UserDto> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userDtos.add(new UserDto(userEntity));
        }
        return userDtos;
    }

    @Override
    public UserDto getById(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        return new UserDto(userEntity);
    }

    @Override
    public void save(UserDto userDto) {
        UserEntity userEntity = new UserEntity(userDto);
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public void update(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user with id :" + userDto.getId()));
        userEntity.updateFromDto(userDto);
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public void delete(Integer id) {

    }
}
