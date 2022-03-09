package com.nnk.springboot.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nnk.springboot.config.PasswordConstraint;
import com.nnk.springboot.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Represents a user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @PasswordConstraint
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Fullname is mandatory")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    private String role;

    /**
     * Build a UserDto with UserEntity values
     *
     * @param userEntity : The UserEntity with values to be copied
     */
    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.fullname = userEntity.getFullname();
        this.role = userEntity.getRole();
    }
}
