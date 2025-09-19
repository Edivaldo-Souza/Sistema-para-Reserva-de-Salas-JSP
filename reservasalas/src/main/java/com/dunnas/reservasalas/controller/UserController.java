package com.dunnas.reservasalas.controller;

import com.dunnas.reservasalas.dto.CreateUserDto;
import com.dunnas.reservasalas.dto.UpdateUserDto;
import com.dunnas.reservasalas.dto.UserDto;
import com.dunnas.reservasalas.enums.UserRole;
import com.dunnas.reservasalas.mappers.UserMapper;
import com.dunnas.reservasalas.model.Role;
import com.dunnas.reservasalas.model.User;
import com.dunnas.reservasalas.services.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Set;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/create")
    public String getCreateUserPage(Model model,
        @RequestHeader(value="Referer",required = false) String referer) {

        String backUrl;

        if(referer == null && !referer.equals("/user")) {
            backUrl = referer;
        }
        else{
            backUrl = "/home";
        }

        model.addAttribute("user", new CreateUserDto());

        model.addAttribute("roles", Arrays.stream(UserRole.values())
                .filter(userRole -> !userRole.equals(UserRole.CLIENTE)).toArray());

        model.addAttribute("backUrl",backUrl);

        return "user/create-user";
    }

    @GetMapping("/update/{id}")
    public String getUpdateUserPage(@PathVariable("id") Long id, Model model){

        User user = userService.getById(id);
        if(user != null){
            UpdateUserDto userDto = userMapper.userToUpdateUserDto(user);
            model.addAttribute("user",userDto);
        }

        return "user/update-user";
    }

    @PostMapping("/save")
    public String createUser(
            @ModelAttribute CreateUserDto user,
            @RequestParam(value = "roles", required = false) String role,
            @RequestParam(value = "sectorId", required = false) Long sectorId,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
       userService.createNewUser(userMapper.createUserDtoToUser(user), role, sectorId, authentication);

       redirectAttributes.addFlashAttribute("success", "Usuário criado com sucesso!");
       return "redirect:/home/home-adm?view=user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute UpdateUserDto user, RedirectAttributes redirectAttributes) {

        userService.update(userMapper.updateUserDtoToUser(user));

        redirectAttributes.addFlashAttribute("success", "Usuário atualizado com sucesso!");
        return "redirect:/home/home-adm?view=user";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);

        return "redirect:/home/home-adm?view=user";
    }
}
