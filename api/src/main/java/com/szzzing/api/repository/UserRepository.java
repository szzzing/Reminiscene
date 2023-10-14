package com.szzzing.api.repository;

import com.szzzing.api.dto.user.UserCheckDto;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.dto.user.UserModifyDto;
import com.szzzing.api.dto.user.UserSelectDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {
    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "userMapper.";

    public int insertOne(UserDto userDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOne", userDto);
    }

    public UserDto selectOneUser(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneById", id);
    }

    public UserDto selectOneByEmail(String email) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneByEmail", email);
    }

    public int selectCountById(UserCheckDto userCheckDto) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectCountById", userCheckDto);
    }

    public int selectCountByEmail(UserCheckDto userCheckDto) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectCountByEmail", userCheckDto);
    }

    public int selectCountByNickname(UserCheckDto userCheckDto) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectCountByNickname", userCheckDto);
    }

    public int updateOne(UserModifyDto userModifyDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOne", userModifyDto);
    }

    public ArrayList<UserDto> selectUserList(UserSelectDto userSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectUserList", userSelectDto);
    }

    public UserDto selectOneByIdEmail(UserCheckDto userCheckDto) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneByIdEmail", userCheckDto);
    }

    public int updateOnePw(UserModifyDto userModifyDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOnePw", userModifyDto);
    }

    public int updateOneEnable(String id) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOneEnable", id);
    }
}