package com.example.demo.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TicketDTO {
    private Long id;
    private String movie;
    private Long room;
    private String holder;
    private int price;
    private int seat;
}
