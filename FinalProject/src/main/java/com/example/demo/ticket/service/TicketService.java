package com.example.demo.ticket.service;

import com.example.demo.exceptions.UnavailableSeatException;
import com.example.demo.movie.model.Movie;
import com.example.demo.movie.repos.MovieRepository;
import com.example.demo.room.model.Room;
import com.example.demo.room.repos.RoomRepository;
import com.example.demo.ticket.mapper.TicketMapper;
import com.example.demo.ticket.model.Ticket;
import com.example.demo.ticket.model.TicketDTO;
import com.example.demo.ticket.repos.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public Ticket findById(Long id){
        return ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket not found: " + id));
    }

    public List<TicketDTO> allTickets(){
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    public TicketDTO createTicket(TicketDTO ticketDTO) throws UnavailableSeatException {
        Ticket ticket = ticketMapper.fromDto(ticketDTO);
        Movie movie = movieRepository.findByTitle(ticketDTO.getMovie()).orElseThrow(() -> new EntityNotFoundException("Error"));
        Room room = roomRepository.findById(ticket.getRoom().getId()).orElseThrow(() -> new EntityNotFoundException("Error"));
        if (validateTicket(ticket.getSeat(), room.getSeats())){
            takeSeat(room, ticket.getSeat());
            ticket.setMovie(movie);
            ticket.setRoom(room);
            addIncome(movie);
            return ticketMapper.toDto(ticketRepository.save(ticket));
        }
        else
            throw new UnavailableSeatException("Error: Seat already taken.");
    }

    public void deleteTicket(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Error. Ticket not found with id " + id));
        Room room = roomRepository.findById(ticket.getRoom().getId()).orElseThrow(() -> new EntityNotFoundException("Error. Room not found with id " + ticket.getRoom().getId()));
        Movie movie = movieRepository.findById(ticket.getMovie().getId()).orElseThrow(() -> new EntityNotFoundException("Error. Movie not found with id " + ticket.getMovie().getId()));
        freeSeat(room, ticket.getSeat());
        removeIncome(movie);
        ticketRepository.deleteById(id);
    }

    public boolean validateTicket(int seat, String seats){
        return seats.charAt(seat - 1) == '0';
    }

    public void takeSeat(Room room, int seat){
        StringBuilder sb = new StringBuilder(room.getSeats());
        sb.setCharAt(seat - 1, '1');
        room.setSeats(sb.toString());
        roomRepository.save(room);
    }

    public void freeSeat(Room room, int seat){
        StringBuilder sb = new StringBuilder(room.getSeats());
        sb.setCharAt(seat - 1, '0');
        room.setSeats(sb.toString());
        roomRepository.save(room);
    }

    public void addIncome(Movie movie){
        movie.setIncome(movie.getIncome() + movie.getTicketPrice());
        movieRepository.save(movie);
    }

    public void removeIncome(Movie movie){
        movie.setIncome(movie.getIncome() - movie.getTicketPrice());
        movieRepository.save(movie);
    }


}
