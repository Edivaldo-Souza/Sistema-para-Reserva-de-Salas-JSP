package com.dunnas.reservasalas.dto;

import com.dunnas.reservasalas.enums.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDto {
    private Long id;
    private Long roomId;
    private Long userId;
    private LocalDateTime startDate;
    private Double duration;
    private RequestStatus status;
}
