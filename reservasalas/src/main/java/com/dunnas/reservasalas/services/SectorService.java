package com.dunnas.reservasalas.services;

import com.dunnas.reservasalas.model.Request;
import com.dunnas.reservasalas.model.Room;
import com.dunnas.reservasalas.model.Sector;
import com.dunnas.reservasalas.repository.RequestRepository;
import com.dunnas.reservasalas.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;
    private final RequestRepository requestRepository;

    @Transactional
    public void createSector(Sector sector) {
        sector.setCashAmount(0D);
        sectorRepository.save(sector);
    }

    @Transactional
    public void updateSector(Sector sector) {
        Optional<Sector> optionalSector = sectorRepository.findById(sector.getId());
        if (optionalSector.isPresent()) {
            sector.setCashAmount(optionalSector.get().getCashAmount());
        }

        sectorRepository.save(sector);
    }

    public List<Sector> findAll() {
        return sectorRepository.findAll();
    }

    public List<Sector> findAllWithRequestFromUserId(Long userId) {
        List<Sector> sectors = findAll();

        for (Sector sector : sectors) {
            if(sector.getRooms()!=null && !sector.getRooms().isEmpty()) {
                for (Room room : sector.getRooms()) {
                    room.setRequests(requestRepository.findAllByUserIdAndRoomId(userId, room.getId()));
                }
            }
        }

        return sectors;
    }

    public List<Sector> findAllWithUserId(Long userId) {
        List<Sector> sectors = sectorRepository.findByUserId(userId);

        for (Sector sector : sectors) {
            if(sector.getRooms()!=null && !sector.getRooms().isEmpty()) {
                for (Room room : sector.getRooms()) {
                    room.setRequests(requestRepository.findAllByRoomId(room.getId()));
                }
            }
        }

        return sectors;
    }

    public Sector findById(Long id) {
        Optional<Sector> sector = sectorRepository.findById(id);
        if (sector.isPresent()) {
            return sector.get();
        }
        return null;
    }

    public void delete(Long id) {
        Optional<Sector> sector = sectorRepository.findById(id);
        if (sector.isPresent()) {
            sectorRepository.delete(sector.get());
        }
    }
}
