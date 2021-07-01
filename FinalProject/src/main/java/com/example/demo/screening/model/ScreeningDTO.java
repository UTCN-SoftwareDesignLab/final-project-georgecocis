package com.example.demo.screening.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ScreeningDTO {
    private Long id;
    private Long movie;
    private Long room;
    private LocalDateTime timeOfScreening;
    private LocalTime screeningDuration;
}
