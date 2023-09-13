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

/**
 * The type Movie controller.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    /**
     * 유저 활동정보 불러오기
     *
     * @param request the request
     * @param movieId 영화코드
     * @return 활동정보
     */
    @GetMapping("/status/{movieId}")
    public ResponseEntity<StatusDto> getStatus(HttpServletRequest request, @PathVariable String movieId) {
        HashMap<String, String> map = new HashMap();
        map.put("movieId", movieId);
        map.put("userId", request.getUserPrincipal().getName());
        StatusDto statusDto = movieService.getStatus(map);
        return new ResponseEntity<StatusDto>(statusDto, statusDto!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 영화 평가 추가
     *
     * @param rateDto the rate dto
     * @param request the request
     * @return the response entity
     */
// 별점
    @PostMapping ("/rate")
    public ResponseEntity addRate(@RequestBody RateDto rateDto, HttpServletRequest request) {
        rateDto.setUserId(request.getUserPrincipal().getName());
        boolean result = movieService.addRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 영화 평가 변경
     *
     * @param rateDto the rate dto
     * @param request the request
     * @return the response entity
     */
    @PutMapping("/rate")
    public ResponseEntity updateRate(@RequestBody RateDto rateDto, HttpServletRequest request) {
        rateDto.setUserId(request.getUserPrincipal().getName());
        boolean result = movieService.updateRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 영화 평가 삭제
     *
     * @param request the request
     * @param movieId the movie id
     * @return the response entity
     */
    @DeleteMapping("/rate/{movieId}")
    public ResponseEntity deleteRate(HttpServletRequest request, @PathVariable String movieId) {
        RateDto rateDto = new RateDto();
        rateDto.setUserId(request.getUserPrincipal().getName());
        rateDto.setMovieId(movieId);
        boolean result = movieService.deleteRate(rateDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 위시리스트 추가
     *
     * @param request the request
     * @param wishDto the wish dto
     * @return the response entity
     */
    @PostMapping("/wish")
    public ResponseEntity addWish(HttpServletRequest request, @RequestBody WishDto wishDto) {
        wishDto.setUserId(request.getUserPrincipal().getName());
        boolean result = movieService.addWish(wishDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 위시리스트 삭제
     *
     * @param request the request
     * @param movieId the movie id
     * @return the response entity
     */
    @DeleteMapping ("/wish/{movieId}")
    public ResponseEntity deleteWish(HttpServletRequest request, @PathVariable String movieId) {
        WishDto wishDto = new WishDto();
        wishDto.setUserId(request.getUserPrincipal().getName());
        wishDto.setMovieId(movieId);
        boolean result = movieService.deleteWish(wishDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저 위시리스트 조회
     *
     * @param commonSelectDto the common select dto
     * @return the wish list
     */
    @GetMapping("/wish")
    public ResponseEntity<MovieListDto> getWishList(@ModelAttribute CommonSelectDto commonSelectDto) {
        MovieListDto result = movieService.getWishList(commonSelectDto);
        return new ResponseEntity(result, result!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 시청중인 영화 추가
     *
     * @param request     the request
     * @param watchingDto the watching dto
     * @return the response entity
     */
    @PostMapping("/watching")
    public ResponseEntity addWatching(HttpServletRequest request, @RequestBody WatchingDto watchingDto) {
        watchingDto.setUserId(request.getUserPrincipal().getName());
        boolean result = movieService.addWatching(watchingDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 시청중인 영화 삭제
     *
     * @param request the request
     * @param movieId the movie id
     * @return the response entity
     */
    @DeleteMapping ("/watching/{movieId}")
    public ResponseEntity deleteWatching(HttpServletRequest request, @PathVariable String movieId) {
        WatchingDto watchingDto = new WatchingDto();
        watchingDto.setUserId(request.getUserPrincipal().getName());
        watchingDto.setMovieId(movieId);
        boolean result = movieService.deleteWatching(watchingDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저 시청중인 영화 조회
     *
     * @param commonSelectDto the common select dto
     * @return the watching list
     */
    @GetMapping("/watching")
    public ResponseEntity<MovieListDto> getWatchingList(@ModelAttribute CommonSelectDto commonSelectDto) {
        MovieListDto result = movieService.getWatchingList(commonSelectDto);
        return new ResponseEntity(result, result!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gets movie.
     *
     * @param id the id
     * @return the movie
     */
    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable String id) {
        MovieDto result = movieService.getMovie(id);
        return new ResponseEntity<>(result, result!=null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Gets movie list.
     *
     * @param movieSelectDto the movie select dto
     * @return the movie list
     */
    @GetMapping("/movies")
    public ResponseEntity<MovieListDto> getMovieList(@ModelAttribute MovieSelectDto movieSelectDto) {
        MovieListDto result = movieService.getMovieList(movieSelectDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Gets movie rank list.
     *
     * @return the movie rank list
     */
    @GetMapping("/movies/rank")
    public ResponseEntity<MovieRankListDto> getMovieRankList() {
        MovieRankListDto result = movieService.getMovieRankList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
