package com.dunnas.reservasalas.controller;

import com.dunnas.reservasalas.dto.RequestDto;
import com.dunnas.reservasalas.enums.RequestStatus;
import com.dunnas.reservasalas.enums.UserRole;
import com.dunnas.reservasalas.mappers.RequestMapper;
import com.dunnas.reservasalas.services.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    public RequestController(RequestService requestService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestMapper = requestMapper;
    }

    @PostMapping("/save")
    public String createRequest(
            @ModelAttribute RequestDto requestDto,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        requestService.create(
                requestMapper.requestDtoToRequest(requestDto),
                requestDto.getRoomId(),
                authentication);

        boolean forClient = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_" + UserRole.CLIENTE));

        redirectAttributes.addFlashAttribute("success", "Solicitação de agendamento submetida");

        return "redirect:/home/client-receptionist";
    }

    @PostMapping("/update/{id}")
    public String updateRequest(
            @PathVariable("id") Long id,
            @RequestParam("status") String status,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ){
        RequestStatus requestStatus = RequestStatus.valueOf(status);

        requestService.update(id,requestStatus);

        redirectAttributes.addFlashAttribute("success","Solicitação cancelda");
        return "redirect:/home/client-receptionist";
    }
}
