package com.szzzing.api.repository;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.common.CommonSelectDto;
import com.szzzing.api.dto.movie.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MovieRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "movieMapper.";

    public int insertOneRate(RateDto rateDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneRate", rateDto);
    }

    public int updateOneRate(RateDto rateDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOneRate", rateDto);
    }

    public int deleteOneRate(RateDto rateDto) {
        return sqlSessionTemplate.delete(NAME_SPACE+"deleteOneRate", rateDto);
    }

    public StatusDto selectOneStatus(HashMap<String, String> map) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneStatus", map);
    }

    public int insertOneWish(WishDto wishDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneWish", wishDto);
    }

    public int deleteOneWish(WishDto wishDto) {
        return sqlSessionTemplate.delete(NAME_SPACE+"deleteOneWish", wishDto);
    }

    public int insertOneWatching(WatchingDto watchingDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneWatching", watchingDto);
    }

    public int deleteOneWatching(WatchingDto watchingDto) {
        return sqlSessionTemplate.delete(NAME_SPACE+"deleteOneWatching", watchingDto);
    }

    public ArrayList<MovieDto> selectWishList(CommonSelectDto commonSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectWishList", commonSelectDto);
    }

    public ArrayList<MovieDto> selectWatchingList(CommonSelectDto commonSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectWatchingList", commonSelectDto);
    }

    public ArrayList<MovieDto> selectMovieList(MovieSelectDto movieSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectMovieList", movieSelectDto);
    }

    public MovieDto selectOneMovie(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneMovie", id);
    }

    public ArrayList<MovieDto> selectMonthBestMovieList() {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectMonthBestMovieList");
    }

    public ArrayList<MovieDto> selectBestRateMovieList() {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectBestRateMovieList");
    }

    public ArrayList<GenreDto> selectGenreList() {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectGenreList");
    }

    public ArrayList<MovieDto> selectSearchList(String query) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectSearchList", query);
    }

    public ArrayList<MovieDto> selectNewMovieList() {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectNewMovieList");
    }
}
