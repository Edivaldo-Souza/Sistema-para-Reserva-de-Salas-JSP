package com.dunnas.reservasalas.services;

import com.dunnas.reservasalas.model.Sector;
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
