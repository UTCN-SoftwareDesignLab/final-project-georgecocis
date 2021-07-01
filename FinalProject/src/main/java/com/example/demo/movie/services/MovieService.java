package com.example.demo.movie.services;

import com.example.demo.movie.mapper.MovieMapper;
import com.example.demo.movie.model.Movie;
import com.example.demo.movie.model.MovieDTO;
import com.example.demo.movie.repos.MovieRepository;
import com.example.demo.socket.MessageBuilder;
import com.example.demo.socket.SocketMessage;
import com.example.demo.user.model.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public Movie findById(Long id){
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found: " + id));
    }

    public List<MovieDTO> allMovies(){
        return movieRepository.findAll().
                stream().map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    public MovieDTO createMovie(MovieDTO movieDTO){
        Movie movie = movieMapper.fromDto(movieDTO);
        movie.setIncome(0);


        MessageBuilder mb = new MessageBuilder();
        simpMessageSendingOperations.convertAndSend("/tickets", new SocketMessage(mb.build(movieDTO)).getMessage());

        return movieMapper.toDto(movieRepository.save(movie));
    }

    public MovieDTO updateMovie(Long id, MovieDTO movieDTO){
        Movie movie = findById(id);
        movie.setTitle(movieDTO.getTitle());
        movie.setDuration(movieDTO.getDuration());
        movie.setDescription(movieDTO.getDescription());
        movie.setTicketPrice(movieDTO.getTicketPrice());
        return movieMapper.toDto(movieRepository.save(movie));
    }

    public MovieDTO get(Long id){
        return movieMapper.toDto(findById(id));
    }

    public void deleteMovie(Long id){
        movieRepository.delete(findById(id));
    }

}
