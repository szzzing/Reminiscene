package com.szzzing.api.controller;

import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
import com.szzzing.api.dto.movie.WatchingDto;
import com.szzzing.api.dto.movie.WishDto;
import com.szzzing.api.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/status/{movieId}")
    public ResponseEntity<StatusDto> getStatus(HttpServletRequest request, @PathVariable String movieId) {
        HashMap<String, String> map = new HashMap();
        map.put("movieId", movieId);
        map.put("userId", request.getUserPrincipal().getName());
        StatusDto statusDto = movieService.getStatus(map);
        return new ResponseEntity<StatusDto>(statusDto, statusDto!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 별점
    @PostMapping ("/rate/{movieId}")
    public ResponseEntity addRate(@RequestBody RateDto rateDto, HttpServletRequest request, @PathVariable String movieId) {
        rateDto.setUserId(request.getUserPrincipal().getName());
        rateDto.setMovieId(movieId);
        boolean result = movieService.addRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/rate/{movieId}")
    public ResponseEntity updateRate(@RequestBody RateDto rateDto, HttpServletRequest request, @PathVariable String movieId) {
        rateDto.setUserId(request.getUserPrincipal().getName());
        rateDto.setMovieId(movieId);
        boolean result = movieService.updateRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/rate/{movieId}")
    public ResponseEntity deleteRate(HttpServletRequest request, @PathVariable String movieId) {
        RateDto rateDto = new RateDto();
        rateDto.setUserId(request.getUserPrincipal().getName());
        rateDto.setMovieId(movieId);
        boolean result = movieService.deleteRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 보고싶어요
    @PostMapping("/wish/{movieId}")
    public ResponseEntity addWish(HttpServletRequest request, @PathVariable String movieId) {
        WishDto wishDto = new WishDto();
        wishDto.setUserId(request.getUserPrincipal().getName());
        wishDto.setMovieId(movieId);
        boolean result = movieService.addWish(wishDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping ("/wish/{movieId}")
    public ResponseEntity deleteWish(HttpServletRequest request, @PathVariable String movieId) {
        WishDto wishDto = new WishDto();
        wishDto.setUserId(request.getUserPrincipal().getName());
        wishDto.setMovieId(movieId);
        boolean result = movieService.deleteWish(wishDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 보는 중
    @PostMapping("/watching/{movieId}")
    public ResponseEntity addWatching(HttpServletRequest request, @PathVariable String movieId) {
        WatchingDto watchingDto = new WatchingDto();
        watchingDto.setUserId(request.getUserPrincipal().getName());
        watchingDto.setMovieId(movieId);
        boolean result = movieService.addWatching(watchingDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping ("/watching/{movieId}")
    public ResponseEntity deleteWatching(HttpServletRequest request, @PathVariable String movieId) {
        WatchingDto watchingDto = new WatchingDto();
        watchingDto.setUserId(request.getUserPrincipal().getName());
        watchingDto.setMovieId(movieId);
        boolean result = movieService.deleteWatching(watchingDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
