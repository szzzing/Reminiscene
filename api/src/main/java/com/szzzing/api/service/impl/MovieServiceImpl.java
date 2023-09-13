package com.szzzing.api.service.impl;

import com.szzzing.api.dto.common.CommonSelectDto;
import com.szzzing.api.dto.movie.*;
import com.szzzing.api.repository.FileRepository;
import com.szzzing.api.repository.MovieRepository;
import com.szzzing.api.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("movieService")
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public boolean addRate(RateDto rateDto) {
        return movieRepository.insertOneRate(rateDto) > 0;
    }

    public boolean updateRate(RateDto rateDto) {
        return movieRepository.updateOneRate(rateDto) > 0;
    }

    public boolean deleteRate(RateDto rateDto) {
        return movieRepository.deleteOneRate(rateDto) > 0;
    }

    public StatusDto getStatus(HashMap<String, String> map) {
        return movieRepository.selectOneStatus(map);
    }

    public boolean addWish(WishDto wishDto) {
        return movieRepository.insertOneWish(wishDto) > 0;
    }

    public boolean deleteWish(WishDto wishDto) {
        return movieRepository.deleteOneWish(wishDto) > 0;
    }

    public boolean addWatching(WatchingDto watchingDto) {
        return movieRepository.insertOneWatching(watchingDto) > 0;
    }

    public boolean deleteWatching(WatchingDto watchingDto) {
        return movieRepository.deleteOneWatching(watchingDto) > 0;
    }

    public MovieListDto getWishList(CommonSelectDto commonSelectDto) {
        if(commonSelectDto.getPage()==null) commonSelectDto.setPage(1);
        commonSelectDto.setOffset();

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setList(movieRepository.selectWishList(commonSelectDto));
        movieListDto.setPage(commonSelectDto.getPage());

        return movieListDto;
    }

    public MovieListDto getWatchingList(CommonSelectDto commonSelectDto) {
        if(commonSelectDto.getPage()==null) commonSelectDto.setPage(1);
        commonSelectDto.setOffset();

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setList(movieRepository.selectWatchingList(commonSelectDto));
        movieListDto.setPage(commonSelectDto.getPage());

        return movieListDto;
    }

    public MovieListDto getMovieList(MovieSelectDto movieSelectDto) {
        if(movieSelectDto.getPage()==null) movieSelectDto.setPage(1);
        movieSelectDto.setOffset();

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setList(movieRepository.selectMovieList(movieSelectDto));
        movieListDto.setPage(movieSelectDto.getPage());

        return movieListDto;
    }

    public MovieDto getMovie(String id) {
        return movieRepository.selectOneMovie(id);
    }

    public MovieRankListDto getMovieRankList() {
        MovieRankListDto movieRankListDto = new MovieRankListDto();
        movieRankListDto.setMonthBestList(movieRepository.selectMonthBestMovieList());
        movieRankListDto.setBestRateList(movieRepository.selectBestRateMovieList());
        return movieRankListDto;
    }
}
