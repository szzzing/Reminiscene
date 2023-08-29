package com.szzzing.api.repository;

import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.dto.user.UserModifyDto;
import com.szzzing.api.dto.user.UserSelectDto;
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

    public UserDto selectOneUser(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneById", id);
    }

    public int selectCountById(HashMap map) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectCountById", map);
    }

    public int selectCountByEmail(HashMap map) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectCountByEmail", map);
    }

    public int selectCountByNickname(HashMap map) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectCountByNickname", map);
    }

    public int updateOne(UserModifyDto userModifyDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOne", userModifyDto);
    }

    public ArrayList<UserDto> selectUserList(UserSelectDto userSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectUserList", userSelectDto);
    }
}