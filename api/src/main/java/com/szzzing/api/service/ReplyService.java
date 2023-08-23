package com.szzzing.api.service;

import com.szzzing.api.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private ReplyRepository replyRepository;
}
