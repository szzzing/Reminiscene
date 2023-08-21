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
        return new ResponseEntity<StatusDto>(statusDto, statusDto!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 별점
    @PostMapping ("/rate")
    public ResponseEntity addRate(@RequestBody RateDto rateDto) {
        boolean result = movieService.addRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/{movieId}/rate/{userId}")
    public ResponseEntity updateRate(@RequestBody RateDto rateDto, @PathVariable String movieId, @PathVariable String userId) {
        rateDto.setMovieId(movieId);
        rateDto.setUserId(userId);
        boolean result = movieService.updateRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/{movieId}/rate/{userId}")
    public ResponseEntity deleteRate(@PathVariable String movieId, @PathVariable String userId) {
        RateDto rateDto = new RateDto();
        rateDto.setMovieId(movieId);
        rateDto.setUserId(userId);
        log.info(rateDto.toString());
        boolean result = movieService.deleteRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
