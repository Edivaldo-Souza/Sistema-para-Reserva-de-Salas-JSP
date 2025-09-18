package com.dunnas.reservasalas.controller;

import com.dunnas.reservasalas.dto.CreateUserDto;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/user")
    public String getUserPage(Model model,
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

        return "create-user";
    }

    @PostMapping("/user/create")
    public String createUser(
            @ModelAttribute CreateUserDto user,
            @RequestParam("roles") String role,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try{
            userService.createNewUser(userMapper.createUserDtoToUser(user), role, authentication);

            redirectAttributes.addFlashAttribute("message", "Usuário criado com sucesso!");
            return "redirect:/home?view=user";
        } catch (Exception e){
            return "redirect:/login";
        }
    }
}
