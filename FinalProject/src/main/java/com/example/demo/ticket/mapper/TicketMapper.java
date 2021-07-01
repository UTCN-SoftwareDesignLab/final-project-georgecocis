package com.example.demo.ticket.mapper;

import com.example.demo.ticket.model.Ticket;
import com.example.demo.ticket.model.TicketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mappings({
            @Mapping(target="movie", expression = "java(ticket.getMovie().getTitle())"),
            @Mapping(target="room", expression = "java(ticket.getRoom().getId())"),

    })
    TicketDTO toDto (Ticket ticket);

    @Mappings({
            @Mapping(target = "movie.title", source = "movie"),
            @Mapping(target = "room.id", source = "room")
    })
    Ticket fromDto (TicketDTO ticketDTO);
}
