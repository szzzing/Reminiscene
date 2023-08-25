package com.szzzing.api.repository;

import com.szzzing.api.dto.reply.ReplyDto;
import com.szzzing.api.dto.reply.ReplySelectDto;
import com.szzzing.api.dto.reply.ReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReplyRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "replyMapper.";

    public int insertOneReply(ReplyDto replyDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneReply", replyDto);
    }

    public int updateOneReply(ReplyDto replyDto) {
        return sqlSessionTemplate.update(NAME_SPACE+"updateOneReply", replyDto);
    }

    public int deleteOneReply(int id) {
        return sqlSessionTemplate.update(NAME_SPACE+"deleteOneReply", id);
    }

    public ArrayList<ReplyDto> selectReviewList(ReplySelectDto replySelectDto) {
        return (ArrayList)sqlSessionTemplate.selectList(NAME_SPACE+"selectReplyList", replySelectDto);
    }

    public int insertOneReport(ReportDto reportDto) {
        return sqlSessionTemplate.insert(NAME_SPACE+"insertOneReport", reportDto);
    }
}