package com.dunnas.reservasalas.dto;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private Double price;
    private Integer capacity;
}
