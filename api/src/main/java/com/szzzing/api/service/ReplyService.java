package com.szzzing.api.service;

import com.szzzing.api.dto.reply.ReplyDto;
import com.szzzing.api.dto.reply.ReplyListDto;
import com.szzzing.api.dto.reply.ReplySelectDto;
import com.szzzing.api.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public boolean addReply(ReplyDto replyDto) {
        return replyRepository.insertOneReply(replyDto) > 0;
    }

    public ReplyListDto getReplyList(ReplySelectDto replySelectDto) {
        ReplyListDto replyListDto = new ReplyListDto();
        replyListDto.setList(replyRepository.selectReviewList(replySelectDto));
        return replyListDto;
    }
}
