package com.dunnas.reservasalas.controller;

import com.dunnas.reservasalas.dto.SectorDto;
import com.dunnas.reservasalas.dto.UserDto;
import com.dunnas.reservasalas.enums.UserRole;
import com.dunnas.reservasalas.mappers.SectorMapper;
import com.dunnas.reservasalas.model.Role;
import com.dunnas.reservasalas.model.Room;
import com.dunnas.reservasalas.model.Sector;
import com.dunnas.reservasalas.model.User;
import com.dunnas.reservasalas.repository.SectorRepository;
import com.dunnas.reservasalas.services.SectorService;
import com.dunnas.reservasalas.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class MainPageController {

    private final UserService userService;
    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;
    private final SectorService sectorService;

    public MainPageController(UserService userService, SectorRepository sectorRepository, SectorMapper sectorMapper, SectorService sectorService) {
        this.userService = userService;
        this.sectorRepository = sectorRepository;
        this.sectorMapper = sectorMapper;
        this.sectorService = sectorService;
    }

    @GetMapping("/client-receptionist")
    public String getClientHomePage(
            Model model,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());

        boolean forClient = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_" + UserRole.CLIENTE));

        if (user != null) {
            List<SectorDto> sectorDtos;
            if (forClient) {

                sectorDtos = sectorService.findAllWithRequestFromUserId(user.getId())
                        .stream().map(sectorMapper::setorToSectorDto).toList();

                model.addAttribute("sectors", sectorDtos);

            } else {
                sectorDtos = sectorService.findAllWithUserId(user.getId())
                        .stream().map(sectorMapper::setorToSectorDto).toList();

                model.addAttribute("sectors", sectorDtos);
            }
        }

        return "home/home-client";
    }

    @GetMapping("/adm")
    public String getAdmHomePage(
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
            List<SectorDto> sectorsDto = sectors.stream().map(sectorMapper::setorToSectorDto).toList();
            model.addAttribute("sectors", sectorsDto);
        }

        model.addAttribute("currentView", view);

        return "home/home-adm";
    }
}
