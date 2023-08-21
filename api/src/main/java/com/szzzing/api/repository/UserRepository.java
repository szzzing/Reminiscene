package com.szzzing.api.repository;

import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.dto.user.UserModifyDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "userMapper.";

    public int insertOne(UserDto userDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOne", userDto);
    }

    public UserDto selectOneById(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneById", id);
    }

    public UserDto selectOneByEmail(HashMap map) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneByEmail", map);
    }

    public UserDto selectOneByNickname(HashMap map) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneByNickname", map);
    }

    public int updateOne(UserModifyDto userModifyDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOne", userModifyDto);
    }
}