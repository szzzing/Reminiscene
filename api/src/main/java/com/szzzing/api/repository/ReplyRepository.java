package com.szzzing.api.repository;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
}