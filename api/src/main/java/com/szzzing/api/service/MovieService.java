package com.szzzing.api.service;

import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
import com.szzzing.api.dto.movie.WatchingDto;
import com.szzzing.api.dto.movie.WishDto;
import com.szzzing.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MovieService {

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
}
