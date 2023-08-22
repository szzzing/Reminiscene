package com.szzzing.api.controller;

import com.szzzing.api.dto.movie.*;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movie/status/{movieId}")
    public ResponseEntity<StatusDto> getStatus(HttpServletRequest request, @PathVariable String movieId) {
        HashMap<String, String> map = new HashMap();
        map.put("movieId", movieId);
        map.put("userId", request.getUserPrincipal().getName());
        StatusDto statusDto = movieService.getStatus(map);
        return new ResponseEntity<StatusDto>(statusDto, statusDto!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 별점
    @PostMapping ("/movie/rate/{movieId}")
    public ResponseEntity addRate(@RequestBody RateDto rateDto, HttpServletRequest request, @PathVariable String movieId) {
        rateDto.setUserId(request.getUserPrincipal().getName());
        rateDto.setMovieId(movieId);
        boolean result = movieService.addRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/movie/rate/{movieId}")
    public ResponseEntity updateRate(@RequestBody RateDto rateDto, HttpServletRequest request, @PathVariable String movieId) {
        rateDto.setUserId(request.getUserPrincipal().getName());
        rateDto.setMovieId(movieId);
        boolean result = movieService.updateRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/movie/rate/{movieId}")
    public ResponseEntity deleteRate(HttpServletRequest request, @PathVariable String movieId) {
        RateDto rateDto = new RateDto();
        rateDto.setUserId(request.getUserPrincipal().getName());
        rateDto.setMovieId(movieId);
        boolean result = movieService.deleteRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 보고싶어요
    @PostMapping("/movie/wish/{movieId}")
    public ResponseEntity addWish(HttpServletRequest request, @PathVariable String movieId) {
        WishDto wishDto = new WishDto();
        wishDto.setUserId(request.getUserPrincipal().getName());
        wishDto.setMovieId(movieId);
        boolean result = movieService.addWish(wishDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping ("/movie/wish/{movieId}")
    public ResponseEntity deleteWish(HttpServletRequest request, @PathVariable String movieId) {
        WishDto wishDto = new WishDto();
        wishDto.setUserId(request.getUserPrincipal().getName());
        wishDto.setMovieId(movieId);
        boolean result = movieService.deleteWish(wishDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 보는 중
    @PostMapping("/movie/watching/{movieId}")
    public ResponseEntity addWatching(HttpServletRequest request, @PathVariable String movieId) {
        WatchingDto watchingDto = new WatchingDto();
        watchingDto.setUserId(request.getUserPrincipal().getName());
        watchingDto.setMovieId(movieId);
        boolean result = movieService.addWatching(watchingDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping ("/movie/watching/{movieId}")
    public ResponseEntity deleteWatching(HttpServletRequest request, @PathVariable String movieId) {
        WatchingDto watchingDto = new WatchingDto();
        watchingDto.setUserId(request.getUserPrincipal().getName());
        watchingDto.setMovieId(movieId);
        boolean result = movieService.deleteWatching(watchingDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 코멘트
    @PostMapping("/comment")
    public ResponseEntity addComment(@RequestBody CommentDto commentDto) {
        boolean result = movieService.addComment(commentDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/comment")
    public ResponseEntity modifyComment(@RequestBody CommentDto commentDto) {
        boolean result = movieService.modifyComment(commentDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/comment")
    public ResponseEntity<CommentDto> getComment(@ModelAttribute CommentDto commentDto) {
        log.info(commentDto.toString());
        CommentDto result = movieService.getComment(commentDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
