package com.example.demo.movie.controller;

import com.example.demo.movie.model.MovieDTO;
import com.example.demo.movie.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.MOVIES;
import static com.example.demo.UrlMapping.MOVIE_ENTITY;

@RestController
@RequiredArgsConstructor
@RequestMapping(MOVIES)
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDTO> allMovies(){
        return movieService.allMovies();
    }

    @PostMapping
    public MovieDTO createMovie(@RequestBody MovieDTO movieDTO){
        return movieService.createMovie(movieDTO);
    }

    @PatchMapping(MOVIE_ENTITY)
    public MovieDTO updateMovie(@PathVariable Long movieId, @RequestBody MovieDTO movieDTO){
        return movieService.updateMovie(movieId, movieDTO);
    }

    @DeleteMapping(MOVIE_ENTITY)
    public void deleteMovie(@PathVariable Long movieId){
        movieService.deleteMovie(movieId);
    }
}
