package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a user (mapping class)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String role;

    /**
     * Build a UserEntity with UserDto values
     *
     * @param userDto : The UserDto with values to be copied
     */
    public UserEntity(UserDto userDto) {
        this.id = userDto.getId();
        this.username = userDto.getUsername().replaceAll("\\s", "");
        // Password managed by UserService
        this.fullname = userDto.getFullname();
        this.role = userDto.getRole();
    }

    /**
     * Updates a UserEntity with UserDto values (except password)
     *
     * @param userDto : The UserDto with values to be updated
     */
    public void updateFromDto(UserDto userDto) {
        if (Objects.equals(userDto.getId(), this.getId())) {
            this.setUsername(userDto.getUsername().replaceAll("\\s", ""));
            // Password managed by UserService
            this.setFullname(userDto.getFullname());
            this.setRole(userDto.getRole());
        }
    }
}
