package com.szzzing.api.service;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.common.CommonSelectDto;
import com.szzzing.api.dto.movie.*;
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

    public boolean addComment(CommentDto commentDto) {
        return movieRepository.insertOneComment(commentDto) > 0;
    }

    public CommentDto getComment(CommentDto commentDto) {
        return movieRepository.selectOneComment(commentDto);
    }

    public boolean modifyComment(CommentDto commentDto) {
        return movieRepository.updateOneComment(commentDto) > 0;
    }

    public MovieDto getMovie(String id) {
        return movieRepository.selectOneMovie(id);
    }

    public WishListDto getWishList(CommonSelectDto commonSelectDto) {
        if(commonSelectDto.getPage()==null) commonSelectDto.setPage(1);
        commonSelectDto.setOffset();

        WishListDto wishListDto = new WishListDto();
        wishListDto.setList(movieRepository.selectWishList(commonSelectDto));
        wishListDto.setPage(commonSelectDto.getPage());

        return wishListDto;
    }

    public WatchingListDto getWatchingList(CommonSelectDto commonSelectDto) {
        if(commonSelectDto.getPage()==null) commonSelectDto.setPage(1);
        commonSelectDto.setOffset();

        WatchingListDto watchingListDto = new WatchingListDto();
        watchingListDto.setList(movieRepository.selectWatchingList(commonSelectDto));
        watchingListDto.setPage(commonSelectDto.getPage());

        return watchingListDto;
    }
}
