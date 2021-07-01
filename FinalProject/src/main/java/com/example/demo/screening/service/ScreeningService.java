package com.example.demo.screening.service;

import com.example.demo.exceptions.UnavailableScreeningTimeException;
import com.example.demo.exceptions.UnavailableSeatException;
import com.example.demo.movie.model.Movie;
import com.example.demo.movie.repos.MovieRepository;
import com.example.demo.room.model.Room;
import com.example.demo.room.repos.RoomRepository;
import com.example.demo.screening.mapper.ScreeningMapper;
import com.example.demo.screening.model.Screening;
import com.example.demo.screening.model.ScreeningDTO;
import com.example.demo.screening.repos.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper screeningMapper;

    public Screening findById(Long id){
        return screeningRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Error. Screening not found: " + id));
    }

    public List<ScreeningDTO> allScreenings(){
        return screeningRepository.findAll()
                .stream().map(screeningMapper::toDto)
                .collect(Collectors.toList());
    }

    public ScreeningDTO createScreening(ScreeningDTO screeningDTO) throws UnavailableScreeningTimeException {
        Screening screening = screeningMapper.fromDto(screeningDTO);
        Movie movie = movieRepository.findById(screeningDTO.getMovie()).orElseThrow(() -> new EntityNotFoundException("Error"));
        Room room = roomRepository.findById(screeningDTO.getRoom()).orElseThrow(() -> new EntityNotFoundException("Error"));
        screening.setRoom(room);
        screening.setMovie(movie);
        screening.setTimeOfScreening(roundHour(screening.getTimeOfScreening()));
        if (validateScreening(screening))
            return screeningMapper.toDto(screeningRepository.save(screening));
        else throw new UnavailableScreeningTimeException("Error. Could not create screening: " + screening.getRoom() + " " + screening.getTimeOfScreening());
    }

    public ScreeningDTO updateScreening(Long id, ScreeningDTO screeningDTO) throws UnavailableScreeningTimeException {
        Screening screening = findById(id);
        Screening screeningTest = screeningMapper.fromDto(screeningDTO);
        Movie movie = movieRepository.findById(screeningDTO.getMovie()).orElseThrow(() -> new EntityNotFoundException("Error"));
        Room room = roomRepository.findById(screeningDTO.getRoom()).orElseThrow(() -> new EntityNotFoundException("Error"));
        screeningTest.setRoom(room);
        screeningTest.setMovie(movie);
        if (validateScreening(screeningTest)){
            screening.setTimeOfScreening(screeningTest.getTimeOfScreening());
            screening.setMovie(screeningTest.getMovie());
            screening.setRoom(screeningTest.getRoom());
            screening.setScreeningDuration(screeningTest.getScreeningDuration());
            return screeningMapper.toDto(screeningRepository.save(screening));
        }
        else throw new UnavailableScreeningTimeException("Error. Could not update screening: " + screening.getId());
    }

    public void deleteScreening(Long id){
        screeningRepository.deleteById(id);
    }

    public Boolean validateScreening(Screening screening){
        LocalDateTime screeningTime = screening.getTimeOfScreening();
        int movieDurationMinimum = roundHour(screening.getScreeningDuration()).getHour();
        while (movieDurationMinimum != 0){
            if (screeningRepository.existsByRoomAndTimeOfScreening(screening.getRoom(), screeningTime.plusHours(movieDurationMinimum - 1)))
                if (!screeningRepository.existsByRoomAndTimeOfScreeningAndId(screening.getRoom(), screeningTime.plusHours(movieDurationMinimum - 1), screening.getId()))
                    return false;
            movieDurationMinimum--;
        }
        return true;
    }

    public LocalTime roundHour(LocalTime time){
        time = time.plusHours(1);
        return time.truncatedTo(ChronoUnit.HOURS);
    }

    public LocalDateTime roundHour(LocalDateTime time){
        if (time.getMinute() > 30)
            time = time.plusHours(1);
        return time.truncatedTo(ChronoUnit.HOURS);
    }
}
