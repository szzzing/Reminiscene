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

    public int insertOneComment(CommentDto commentDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneComment", commentDto);
    }

    public CommentDto selectOneComment(CommentDto commentDto) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneComment", commentDto);
    }

    public int updateOneComment(CommentDto commentDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOneComment", commentDto);
    }

    public StasticsDto selectOneStatistics(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneStatistics", id);
    }

    public ArrayList<MovieDto> selectWishList(CommonSelectDto commonSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectWishList", commonSelectDto);
    }

    public ArrayList<MovieDto> selectWatchingList(CommonSelectDto commonSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectWatchingList", commonSelectDto);
    }

    public int insertOneMovie(MovieDto movieDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneMovie", movieDto);
    }

    public ArrayList<MovieDto> selectMovieList(MovieSelectDto movieSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectMovieList", movieSelectDto);
    }

    public MovieDto selectOneMovie(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneMovie", id);
    }
}
