package com.example.demo.room.service;

import com.example.demo.room.model.Room;
import com.example.demo.room.repos.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room findRoomById(Long id){
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found: " + id));
    }

    public Boolean checkSeat(String seats, int seat) {
        return seats.charAt(seat - 1) == '0';
    }

    public boolean isSeatAvailable(Long id, int seat){
        Room room = findRoomById(id);
        if (seat > room.getSeats().length())
            throw new EntityNotFoundException("Error. Seat number not found: " + seat);
        return checkSeat(room.getSeats(), seat);
    }

    public Room createRoom(int capacity){
        Room room = new Room();
        room.setCapacity(capacity);
//        room.setSeats(new boolean[capacity]);
        room.setSeats(createSeats(capacity));
        return roomRepository.save(room);
    }

    public Room freeAllSeats(Long id){
        Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        room.setSeats(createSeats(room.getCapacity()));
        return roomRepository.save(room);
    }

    public String createSeats(int capacity){
        return "0".repeat(capacity);
    }
}
