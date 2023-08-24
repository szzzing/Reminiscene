package com.szzzing.api.repository;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.comment.CommentSelectDto;
import com.szzzing.api.dto.comment.LikeDto;
import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
import com.szzzing.api.dto.movie.WatchingDto;
import com.szzzing.api.dto.movie.WishDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "commentMapper.";

    public int insertOneComment(CommentDto commentDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneComment", commentDto);
    }

    public int selectCommentListCount(CommentSelectDto commentSelectDto) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectCommentListCount", commentSelectDto);
    }

    public CommentDto selectOneComment(CommentSelectDto commentSelectDto) {
        return sqlSessionTemplate.selectOne(NAME_SPACE+"selectOneComment", commentSelectDto);
    }

    public int updateOneComment(CommentDto commentDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOneComment", commentDto);
    }

    public ArrayList<CommentDto> selectCommentList(CommentSelectDto commentSelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectCommentList", commentSelectDto);
    }

    public int insertOneLike(LikeDto likeDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneLike", likeDto);
    }

    public int deleteOneLike(LikeDto likeDto) {
        return sqlSessionTemplate.delete(NAME_SPACE+"deleteOneLike", likeDto);
    }
}
