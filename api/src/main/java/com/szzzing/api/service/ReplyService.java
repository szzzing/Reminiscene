package com.szzzing.api.service;

import com.szzzing.api.dto.reply.ReplyDto;
import com.szzzing.api.dto.reply.ReplyListDto;
import com.szzzing.api.dto.reply.ReplySelectDto;
import com.szzzing.api.dto.reply.ReportDto;
import com.szzzing.api.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface ReplyService {

    public boolean addReply(ReplyDto replyDto);

    public boolean modifyReply(ReplyDto replyDto);

    public boolean deleteReply(int id);

    public ReplyListDto getReplyList(ReplySelectDto replySelectDto);

    public boolean addReport(ReportDto reportDto);
}
