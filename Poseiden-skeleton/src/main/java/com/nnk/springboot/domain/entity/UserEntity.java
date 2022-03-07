package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public UserEntity(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public UserEntity(UserDto userDto) {
        this.username = userDto.getUsername();
        this.fullname = userDto.getFullname();
        this.role = userDto.getRole();
    }

    public void updateFromDto(UserDto userDto) {
        if (userDto.getId().equals(this.id)) {
            this.setUsername(userDto.getUsername());
            this.setFullname(userDto.getFullname());
            this.setRole(userDto.getRole());
        }
    }
}
