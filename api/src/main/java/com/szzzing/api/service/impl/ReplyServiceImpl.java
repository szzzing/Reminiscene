package com.szzzing.api.service.impl;

import com.szzzing.api.dto.reply.ReplyDto;
import com.szzzing.api.dto.reply.ReplyListDto;
import com.szzzing.api.dto.reply.ReplySelectDto;
import com.szzzing.api.dto.reply.ReportDto;
import com.szzzing.api.repository.ReplyRepository;
import com.szzzing.api.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type Reply service.
 */
@Service("replyService")
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    public boolean addReply(ReplyDto replyDto) {
        return replyRepository.insertOneReply(replyDto) > 0;
    }

    public boolean modifyReply(ReplyDto replyDto) {
        return replyRepository.updateOneReply(replyDto) > 0;
    }

    public boolean deleteReply(int id) {
        return replyRepository.deleteOneReply(id) > 0;
    }

    public ReplyListDto getReplyList(ReplySelectDto replySelectDto) {
        ReplyListDto replyListDto = new ReplyListDto();
        replyListDto.setList(replyRepository.selectReplyList(replySelectDto));
        return replyListDto;
    }

    public boolean addReport(ReportDto reportDto) {
        return replyRepository.insertOneReport(reportDto) > 0;
    }
}
