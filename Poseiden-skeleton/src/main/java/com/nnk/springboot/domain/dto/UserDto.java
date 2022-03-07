package com.nnk.springboot.domain.dto;

import com.nnk.springboot.config.PasswordConstraint;
import com.nnk.springboot.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @PasswordConstraint
    private String password;
    @NotBlank(message = "Fullname is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.fullname = userEntity.getFullname();
        this.role = userEntity.getRole();
    }
}
