package com.example.demo.ticket.controller;

import com.example.demo.exceptions.UnavailableSeatException;
import com.example.demo.movie.model.MovieDTO;
import com.example.demo.movie.services.MovieService;
import com.example.demo.report.ReportGenerator;
import com.example.demo.screening.model.ScreeningDTO;
import com.example.demo.screening.service.ScreeningService;
import com.example.demo.ticket.model.TicketDTO;
import com.example.demo.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RequestMapping(TICKETS)
@RestController
@RequiredArgsConstructor
public class TicketController {

    private final MovieService movieService;
    private final TicketService ticketService;
    private final ScreeningService screeningService;
    private final ReportGenerator reportGenerator;

    @GetMapping
    public List<TicketDTO> allTickets(){
        return ticketService.allTickets();
    }

    @GetMapping(TICKET_MOVIE_ENTITY)
    public MovieDTO getMovie(@PathVariable Long tmovieId){
        return movieService.get(tmovieId);
    }

    @GetMapping(TICKET_MOVIES)
    public List<MovieDTO> allMovies(){
        return movieService.allMovies();
    }

    @PostMapping()
    public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO) throws UnavailableSeatException {
        return ticketService.createTicket(ticketDTO);
    }

    @DeleteMapping(TICKET_ENTITY)
    public void deleteTicket(@PathVariable Long ticketId){
        ticketService.deleteTicket(ticketId);
    }

    @GetMapping(PDF)
    public void exportReport() throws IOException {
        reportGenerator.export(movieService.allMovies());
    }

    @GetMapping(TICKET_SCREENINGS)
    public List<ScreeningDTO> allScreenings(){
        return screeningService.allScreenings();
    }
}
