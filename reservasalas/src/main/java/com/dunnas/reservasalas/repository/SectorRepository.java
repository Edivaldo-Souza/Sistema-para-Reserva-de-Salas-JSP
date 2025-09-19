package com.dunnas.reservasalas.repository;

import com.dunnas.reservasalas.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findByUserId(Long id);
}
