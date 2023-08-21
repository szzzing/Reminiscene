package com.szzzing.api.repository;

import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
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

    public StatusDto selectOneStatus(HashMap<String, String> map) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneStatus", map);
    }
}
