package com.dunnas.reservasalas.mappers;

import com.dunnas.reservasalas.dto.RoomDto;
import com.dunnas.reservasalas.dto.SectorDto;
import com.dunnas.reservasalas.model.Room;
import com.dunnas.reservasalas.model.Sector;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SectorMapper {

    public SectorDto setorToSectorDto(Sector sector) {
        SectorDto sectorDto = new SectorDto();
        sectorDto.setId(sector.getId());
        sectorDto.setName(sector.getName());
        sectorDto.setCashAmount(sector.getCashAmount());

        if(sector.getUser()!=null){
            sectorDto.setUsername(sector.getUser().getUsername());
        }

        List<RoomDto> rooms = new ArrayList<>();
        for (Room room : sector.getRooms()) {
            RoomDto roomDto = new RoomDto();
            roomDto.setId(room.getId());
            roomDto.setName(room.getName());
            roomDto.setCapacity(room.getCapacity());
            roomDto.setPrice(room.getPrice());
            rooms.add(roomDto);
        }

        sectorDto.setRooms(rooms);
        return sectorDto;
    }

    public Sector sectorDtoToSector(SectorDto sectorDto) {
        Sector sector = new Sector();
        sector.setId(sectorDto.getId());
        sector.setName(sectorDto.getName());
        List<Room> rooms = new ArrayList<>();
        if(sectorDto.getRooms()!=null && !sectorDto.getRooms().isEmpty()) {
            for(RoomDto roomDto : sectorDto.getRooms()) {
                Room room = new Room();
                room.setId(roomDto.getId());
                room.setName(roomDto.getName());
                room.setPrice(roomDto.getPrice());
                room.setCapacity(roomDto.getCapacity());
                room.setSector(sector);
                rooms.add(room);
            }
        }
        sector.setRooms(rooms);
        return sector;
    }
}
