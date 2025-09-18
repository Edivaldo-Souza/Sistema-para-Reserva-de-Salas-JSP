package com.dunnas.reservasalas.dto;

import com.dunnas.reservasalas.enums.UserRole;
import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private UserRole role;
}
