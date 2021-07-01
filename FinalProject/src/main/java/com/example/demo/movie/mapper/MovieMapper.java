package com.example.demo.movie.mapper;

import com.example.demo.movie.model.Movie;
import com.example.demo.movie.model.MovieDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO toDto(Movie movie);

    Movie fromDto(MovieDTO movieDTO);
}
