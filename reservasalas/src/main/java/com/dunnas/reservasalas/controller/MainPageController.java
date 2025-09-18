package com.dunnas.reservasalas.controller;

import com.dunnas.reservasalas.dto.UserDto;
import com.dunnas.reservasalas.enums.UserRole;
import com.dunnas.reservasalas.model.Role;
import com.dunnas.reservasalas.model.Room;
import com.dunnas.reservasalas.model.Sector;
import com.dunnas.reservasalas.model.User;
import com.dunnas.reservasalas.repository.SectorRepository;
import com.dunnas.reservasalas.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final UserService userService;
    private final SectorRepository sectorRepository;

    public MainPageController(UserService userService, SectorRepository sectorRepository) {
        this.userService = userService;
        this.sectorRepository = sectorRepository;
    }

    @GetMapping("/home")
    public String mainPage(
        @RequestParam(defaultValue = "users") String view,
        Model model
    ) {
        if(view.equals("users")){
            List<User> users = userService.getAllUsers();
            List<UserDto> usersDto = new ArrayList<>();
            for(User user : users){
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setName(user.getUsername());
                userDto.setRole(UserRole.valueOf(
                        user.getRoles()
                                .stream().map(Role::getName)
                                .toList()
                                .getFirst()
                        )
                );
                usersDto.add(userDto);
            }
            model.addAttribute("users", usersDto);
        }
        else if(view.equals("sectors")){
            List<Sector> sectors = sectorRepository.findAll();
            model.addAttribute("sectors", sectors);
        }

        model.addAttribute("currentView", view);

        return "home";
    }
}
