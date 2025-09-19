package com.dunnas.reservasalas.mappers;

import com.dunnas.reservasalas.dto.RequestDto;
import com.dunnas.reservasalas.enums.RequestStatus;
import com.dunnas.reservasalas.model.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    public Request requestDtoToRequest(RequestDto dto) {
        Request request = new Request();
        request.setId(dto.getId());
        request.setDuration(dto.getDuration());
        request.setStartDate(dto.getStartDate());

        return request;
    }

    public RequestDto requestToRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setDuration(request.getDuration());
        requestDto.setStartDate(request.getStartDate());
        requestDto.setStatus(RequestStatus.valueOf(request.getStatus()));
        return requestDto;
    }
}
