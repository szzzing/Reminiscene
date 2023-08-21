package com.szzzing.api.repository;

import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
import com.szzzing.api.dto.movie.WatchingDto;
import com.szzzing.api.dto.movie.WishDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
@RequiredArgsConstructor
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
}
