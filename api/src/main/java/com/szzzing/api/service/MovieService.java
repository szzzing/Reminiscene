package com.szzzing.api.service;

import com.szzzing.api.dto.common.CommonSelectDto;
import com.szzzing.api.dto.movie.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface MovieService {

    public boolean addRate(RateDto rateDto);

    public boolean updateRate(RateDto rateDto);

    public boolean deleteRate(RateDto rateDto);

    public MovieListDto getRateList(CommonSelectDto commonSelectDto);

    public StatusDto getStatus(HashMap<String, String> map);

    public boolean addWish(WishDto wishDto);

    public boolean deleteWish(WishDto wishDto);

    public boolean addWatching(WatchingDto watchingDto);

    public boolean deleteWatching(WatchingDto watchingDto);

    public MovieListDto getWishList(CommonSelectDto commonSelectDto);

    public MovieListDto getWatchingList(CommonSelectDto commonSelectDto);

    public MovieListDto getMovieList(MovieSelectDto movieSelectDto);

    public MovieDto getMovie(String id);

    public MovieRankListDto getMovieRankList();

    public ArrayList<GenreDto> getGenreList();

    public ArrayList<MovieDto> getSearchList(String query);
}
