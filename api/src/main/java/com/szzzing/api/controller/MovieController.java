package com.szzzing.api.controller;

import com.szzzing.api.dto.common.CommonSelectDto;
import com.szzzing.api.dto.movie.*;
import com.szzzing.api.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
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
    @PostMapping ("/rate")
    public ResponseEntity addRate(@RequestBody RateDto rateDto, HttpServletRequest request) {
        rateDto.setUserId(request.getUserPrincipal().getName());
        boolean result = movieService.addRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/rate")
    public ResponseEntity updateRate(@RequestBody RateDto rateDto, HttpServletRequest request) {
        rateDto.setUserId(request.getUserPrincipal().getName());
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
    @PostMapping("/wish")
    public ResponseEntity addWish(HttpServletRequest request, @RequestBody WishDto wishDto) {
        wishDto.setUserId(request.getUserPrincipal().getName());
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
    @GetMapping("/wish")
    public ResponseEntity<MovieListDto> getWishList(@ModelAttribute CommonSelectDto commonSelectDto) {
        MovieListDto result = movieService.getWishList(commonSelectDto);
        return new ResponseEntity(result, result!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 보는 중
    @PostMapping("/watching")
    public ResponseEntity addWatching(HttpServletRequest request, @RequestBody WatchingDto watchingDto) {
        watchingDto.setUserId(request.getUserPrincipal().getName());
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
    @GetMapping("/watching")
    public ResponseEntity<MovieListDto> getWatchingList(@ModelAttribute CommonSelectDto commonSelectDto) {
        MovieListDto result = movieService.getWatchingList(commonSelectDto);
        return new ResponseEntity(result, result!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 영화 정보
    @GetMapping("/statistics/{id}")
    public ResponseEntity<StasticsDto> getStatistics(@PathVariable String id) {
        StasticsDto result = movieService.getStatistics(id);
        return new ResponseEntity<>(result, result!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 영화 삽입
    @PostMapping("/movie")
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movieDto) {
        int result = movieService.addMovie(movieDto);
        return new ResponseEntity<>(movieDto, HttpStatus.CREATED);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable String id) {
        MovieDto result = movieService.getMovie(id);
        log.info(result.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<MovieListDto> getMovieList(@ModelAttribute MovieSelectDto movieSelectDto) {
        MovieListDto result = movieService.getMovieList(movieSelectDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
