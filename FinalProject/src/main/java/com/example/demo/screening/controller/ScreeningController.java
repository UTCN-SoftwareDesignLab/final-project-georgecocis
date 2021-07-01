package com.example.demo.screening.controller;

import com.example.demo.exceptions.UnavailableScreeningTimeException;
import com.example.demo.screening.model.ScreeningDTO;
import com.example.demo.screening.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.SCREENINGS;
import static com.example.demo.UrlMapping.SCREENING_ENTITY;

@RequestMapping(SCREENINGS)
@RestController
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping
    public List<ScreeningDTO> allScreenings(){
        return screeningService.allScreenings();
    }

    @PostMapping
    public ScreeningDTO createScreening(@RequestBody ScreeningDTO screeningDTO) throws UnavailableScreeningTimeException {
        return screeningService.createScreening(screeningDTO);
    }

    @PatchMapping(SCREENING_ENTITY)
    public ScreeningDTO updateScreening(@PathVariable Long screeningId, @RequestBody ScreeningDTO screeningDTO) throws UnavailableScreeningTimeException {
        return screeningService.updateScreening(screeningId, screeningDTO);
    }

    @DeleteMapping(SCREENING_ENTITY)
    public void deleteScreening(@PathVariable Long screeningId){
        screeningService.deleteScreening(screeningId);
    }
}
