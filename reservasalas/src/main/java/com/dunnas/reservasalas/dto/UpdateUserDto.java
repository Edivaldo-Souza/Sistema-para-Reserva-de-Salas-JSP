package com.dunnas.reservasalas.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
}
