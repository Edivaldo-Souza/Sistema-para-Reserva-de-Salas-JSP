package com.dunnas.reservasalas.repository;

import com.dunnas.reservasalas.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query("""
    SELECT r FROM Request r WHERE r.user.id = :userId AND r.room.id = :roomId
""")
    List<Request> findAllByUserIdAndRoomId(Long userId, Long roomId);

    List<Request> findAllByRoomId(Long roomId);
}
