package com.szzzing.api.controller;

import com.szzzing.api.dto.reply.ReplyDto;
import com.szzzing.api.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/reply")
    public ResponseEntity addReply(@RequestBody ReplyDto replyDto) {
        log.info(replyDto.toString());
        return null;
    }
}
