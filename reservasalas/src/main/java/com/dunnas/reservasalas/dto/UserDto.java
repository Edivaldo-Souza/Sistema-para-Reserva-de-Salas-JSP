package com.dunnas.reservasalas.dto;

import com.dunnas.reservasalas.enums.UserRole;
import com.dunnas.reservasalas.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private UserRole role;
}
