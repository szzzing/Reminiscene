package com.szzzing.api.service;

import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
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

    public StatusDto getStatus(HashMap<String, String> map) {
        return movieRepository.selectOneStatus(map);
    }
}
