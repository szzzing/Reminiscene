package com.szzzing.api.controller;

import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
import com.szzzing.api.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{movieId}/status/{userId}")
    public ResponseEntity<StatusDto> getStatus(@PathVariable String movieId, @PathVariable String userId) {
        HashMap<String, String> map = new HashMap();
        map.put("movieId", movieId);
        map.put("userId", userId);
        StatusDto statusDto = movieService.getStatus(map);
        log.info(statusDto.toString());
        return new ResponseEntity<StatusDto>(statusDto, statusDto!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping ("/rate")
    public ResponseEntity addRate(@RequestBody RateDto rateDto) {
        log.info(rateDto.toString());
        boolean result = movieService.addRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/rate")
    public ResponseEntity updateRate(@RequestBody RateDto rateDto) {
        boolean result = movieService.updateRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
