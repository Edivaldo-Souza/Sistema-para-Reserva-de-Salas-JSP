package com.dunnas.reservasalas.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectorDto {
    private Long id;
    private String name;
    private Double cashAmount;
    private String username;
    private List<RoomDto> rooms = new ArrayList<>();
}
