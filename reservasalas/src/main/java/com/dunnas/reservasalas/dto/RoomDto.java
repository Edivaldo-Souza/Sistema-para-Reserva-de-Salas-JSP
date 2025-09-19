package com.dunnas.reservasalas.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private Double price;
    private Integer capacity;
    private List<RequestDto> requests;
}
