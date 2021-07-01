package com.example.demo.screening.mapper;

import com.example.demo.screening.model.Screening;
import com.example.demo.screening.model.ScreeningDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ScreeningMapper {
    @Mappings({
            @Mapping(target="movie", expression = "java(screening.getMovie().getId())"),
            @Mapping(target="room", expression = "java(screening.getRoom().getId())"),

    })
    ScreeningDTO toDto(Screening screening);

    @Mappings({
            @Mapping(target = "movie.id", source = "movie"),
            @Mapping(target = "room.id", source = "room")
    })
    Screening fromDto(ScreeningDTO screeningDTO);

}
