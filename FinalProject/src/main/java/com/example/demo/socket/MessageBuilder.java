package com.example.demo.socket;

import com.example.demo.movie.model.MovieDTO;
import lombok.Data;

@Data
public class MessageBuilder {

    public String build(MovieDTO movieDTO){
        StringBuilder sb = new StringBuilder();
        sb.append("A new movie has been added!")
                .append('\n').append("Title: ").append(movieDTO.getTitle())
                .append('\n').append("Description: ").append(movieDTO.getDescription())
                .append("\n").append("Duration: ").append(movieDTO.getDuration())
                .append("\n").append("Ticket price: ").append(movieDTO.getTicketPrice());

        return sb.toString();
    }

}
