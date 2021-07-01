package com.example.demo.screening.repos;

import com.example.demo.room.model.Room;
import com.example.demo.screening.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    Boolean existsByRoomAndTimeOfScreening(Room room, LocalDateTime time);

    Boolean existsByRoomAndTimeOfScreeningAndId(Room room, LocalDateTime time, Long id);
}
