package com.dunnas.reservasalas.mappers;

import com.dunnas.reservasalas.dto.CreateUserDto;
import com.dunnas.reservasalas.dto.UpdateUserDto;
import com.dunnas.reservasalas.dto.UserDto;
import com.dunnas.reservasalas.model.Role;
import com.dunnas.reservasalas.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {
    public User createUserDtoToUser(CreateUserDto userDto) {

        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("As senhas não estão iguais");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        return user;
    }

    public UpdateUserDto userToUpdateUserDto(User user) {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setId(user.getId());
        updateUserDto.setUsername(user.getUsername());

        return updateUserDto;
    }

    public User updateUserDtoToUser(UpdateUserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
