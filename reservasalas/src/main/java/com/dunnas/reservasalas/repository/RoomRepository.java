package com.dunnas.reservasalas.repository;

import com.dunnas.reservasalas.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
