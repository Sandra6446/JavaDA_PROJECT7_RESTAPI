package com.nnk.springboot.service;

import com.nnk.springboot.domain.dto.UserDto;
import com.nnk.springboot.domain.entity.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.config.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages the UserEntity data
 */
@Service
@Transactional
@AllArgsConstructor
public class UserService implements CrudService<UserDto> {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     * Gets all UserEntities in database
     *
     * @return A list of corresponding UserDto
     */
    @Override
    public List<UserDto> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userDtos.add(new UserDto(userEntity));
        }
        return userDtos;
    }

    /**
     * Gets a specific UserEntity in database
     *
     * @param id : The id to be found
     * @return The corresponding UserDto
     */
    @Override
    public UserDto getById(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        return new UserDto(userEntity);
    }

    /**
     * Saves a UserEntity in database
     *
     * @param userDto : The UserDto with values to be saved
     */
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

    /**
     * Updates a UserEntity in database
     *
     * @param userDto : The UserDto with new values
     */
    @Override
    public void update(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user with id :" + userDto.getId()));
        userEntity.updateFromDto(userDto);
        userEntity.setPassword(userDetailsService.encode(userDto.getPassword()));
        userRepository.saveAndFlush(userEntity);
    }

    /**
     * Deletes a UserEntity in database
     *
     * @param id : The id of the UserEntity to be deleted
     */
    @Override
    public void delete(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user with id :" + id));
        userRepository.deleteById(id);
    }
}
