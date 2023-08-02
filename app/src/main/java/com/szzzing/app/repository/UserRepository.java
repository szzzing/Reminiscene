package com.szzzing.app.repository;

import com.szzzing.app.domain.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "userMapper.";

    public User insert(User user) {
        return user;
    }
    public ArrayList<User> selectAll() {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectAll");
    }

    public User login() {
        return null;
    }

    public User selectOneById(String id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneById", id);
    }
}