package com.szzzing.api.repository;

import com.szzzing.api.domain.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "userMapper.";

    public int insertOne(User user) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOne", user);
    }

    public ArrayList<User> selectAll() {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectAll");
    }

    public User selectOneById(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneById", id);
    }

    public User selectOneByEmail(String email) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneByEmail", email);
    }

    public User selectOneByNickname(String nickname) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneByNickname", nickname);
    }

    public int updateOne(User user) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOne", user);
    }
}