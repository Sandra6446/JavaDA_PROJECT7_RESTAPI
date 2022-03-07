package com.nnk.springboot.service;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.dto.UserDto;
import com.nnk.springboot.domain.entity.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements CrudService<UserDto> {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

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
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(userDto.getUsername());
        if (userEntityOptional.isPresent()) {
            logger.error("Invalid user with username :" + userDto.getUsername());
            throw new IllegalArgumentException("Invalid user with username :" + userDto.getUsername());
        } else {
            UserEntity userEntity = new UserEntity(userDto);
            userEntity.setPassword(userDetailsService.encode(userDto.getPassword()));
            userRepository.saveAndFlush(userEntity);
        }
    }

    @Override
    public void update(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user with id :" + userDto.getId()));
        userEntity.updateFromDto(userDto);
        userEntity.setPassword(userDetailsService.encode(userDto.getPassword()));
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public void delete(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user with id :" + id));
        userRepository.deleteById(id);
    }
}
