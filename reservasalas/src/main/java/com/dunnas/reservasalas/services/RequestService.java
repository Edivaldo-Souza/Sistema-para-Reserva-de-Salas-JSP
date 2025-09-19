package com.dunnas.reservasalas.services;

import com.dunnas.reservasalas.enums.RequestStatus;
import com.dunnas.reservasalas.model.Request;
import com.dunnas.reservasalas.model.Room;
import com.dunnas.reservasalas.model.User;
import com.dunnas.reservasalas.repository.RequestRepository;
import com.dunnas.reservasalas.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final RoomRepository roomRepository;
    private final UserService userService;

    public void create(Request request, Long roomId, Authentication authentication) {
        Optional<Room> room = roomRepository.findById(roomId);
        if(room.isPresent()) {
            request.setRoom(room.get());
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());

        if(user != null) {
            request.setUser(user);
        }

        request.setStatus(RequestStatus.SUBMETIDO.name());
        requestRepository.save(request);
    }

    public void update(Long id, RequestStatus newStatus){
        Optional<Request> request = requestRepository.findById(id);
        if(request.isPresent()) {
            request.get().setStatus(newStatus.name());
            requestRepository.save(request.get());
        }

    }

}
