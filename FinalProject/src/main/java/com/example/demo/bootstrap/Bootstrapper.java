package com.example.demo.bootstrap;

import com.example.demo.exceptions.UnavailableScreeningTimeException;
import com.example.demo.exceptions.UnavailableSeatException;
import com.example.demo.mail.MailService;
import com.example.demo.movie.model.Movie;
import com.example.demo.movie.model.MovieDTO;
import com.example.demo.movie.repos.MovieRepository;
import com.example.demo.movie.services.MovieService;
import com.example.demo.room.model.Room;
import com.example.demo.room.repos.RoomRepository;
import com.example.demo.room.service.RoomService;
import com.example.demo.screening.model.ScreeningDTO;
import com.example.demo.screening.repos.ScreeningRepository;
import com.example.demo.screening.service.ScreeningService;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.sms.SmsModel;
import com.example.demo.sms.SmsService;
import com.example.demo.ticket.model.TicketDTO;
import com.example.demo.ticket.repos.TicketRepository;
import com.example.demo.ticket.service.TicketService;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.repos.RoleRepository;
import com.example.demo.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final TicketRepository ticketRepository;

    private final AuthService authService;
    private final RoomService roomService;
    private final MovieService movieService;
    private final ScreeningService screeningService;
    private final TicketService ticketService;

    private final MailService mailService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap){
            emptyRepos();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .role(value)
                                .build()
                );
            }
            createUsers();
            createRooms();
            createMovies();
            createScreenings();
            createTickets();
        }
    }

    void emptyRepos(){
        screeningRepository.deleteAll();
        ticketRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        movieRepository.deleteAll();
        roomRepository.deleteAll();
    }

    void createUsers(){
        authService.register(SignupRequest.builder()
                .email("admin@email.com")
                .username("admin")
                .password("password")
                .roles(Set.of("ADMINISTRATOR"))
                .build()
        );

        authService.register(SignupRequest.builder()
                .email("employee@email.com")
                .username("employee")
                .password("password")
                .roles(Set.of("EMPLOYEE"))
                .build()
        );
    }

    void createRooms(){
        roomService.createRoom(50);
        roomService.createRoom(60);
        roomService.createRoom(70);
    }

    void createMovies(){
        movieService.createMovie(MovieDTO.builder()
                    .title("First movie")
                    .description("Good movie overall")
                    .duration(LocalTime.of(2,34))
                    .ticketPrice(25)
                    .build());

        movieService.createMovie(MovieDTO.builder()
                .title("Second movie")
                .description("It works")
                .duration(LocalTime.of(1,50))
                .ticketPrice(22)
                .build());
    }

    void createScreenings() throws UnavailableScreeningTimeException {
        Room room1 = roomRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Wrong room ID"));
        Room room2 = roomRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("Wrong room ID"));
        Movie movie1 = movieRepository.findByTitle("First movie").orElseThrow(() -> new EntityNotFoundException("Wrong movie title"));
        Movie movie2 = movieRepository.findByTitle("Second movie").orElseThrow(() -> new EntityNotFoundException("Wrong movie title"));

        screeningService.createScreening(ScreeningDTO.builder()
                .movie(movie1.getId())
                .room(room1.getId())
                .timeOfScreening(LocalDateTime.now())
                .screeningDuration(movie1.getDuration())
                .build());

        screeningService.createScreening(ScreeningDTO.builder()
                .movie(movie2.getId())
                .room(room2.getId())
                .timeOfScreening(LocalDateTime.now())
                .screeningDuration(movie2.getDuration())
                .build());
    }

    void createTickets() throws UnavailableSeatException {
        Room room1 = roomRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Wrong room ID"));
        Room room2 = roomRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("Wrong room ID"));
        Movie movie1 = movieRepository.findByTitle("First movie").orElseThrow(() -> new EntityNotFoundException("Wrong movie title"));
        Movie movie2 = movieRepository.findByTitle("Second movie").orElseThrow(() -> new EntityNotFoundException("Wrong movie title"));


        ticketService.createTicket(TicketDTO.builder()
                    .movie(movie1.getTitle())
                    .room(room1.getId())
                    .holder("Cocis George")
                    .price(movie1.getTicketPrice())
                    .seat(1)
                    .build());

        ticketService.createTicket(TicketDTO.builder()
                .movie(movie1.getTitle())
                .room(room1.getId())
                .holder("Cocis Radu")
                .price(movie1.getTicketPrice())
                .seat(2)
                .build());

        ticketService.createTicket(TicketDTO.builder()
                .movie(movie2.getTitle())
                .room(room2.getId())
                .holder("Roxana Ciocian")
                .price(movie2.getTicketPrice())
                .seat(5)
                .build());
    }
}
