package com.dunnas.reservasalas.controller;

import com.dunnas.reservasalas.model.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomController {

    @GetMapping("/rooms")
    public String getAllRooms(Model model) {
        return "rooms";
    }
}
