package com.szzzing.api.controller;

import com.szzzing.api.dto.reply.ReplyDto;
import com.szzzing.api.dto.reply.ReplyListDto;
import com.szzzing.api.dto.reply.ReplySelectDto;
import com.szzzing.api.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/reply")
    public ResponseEntity addReply(@RequestBody ReplyDto replyDto) {
        boolean result = replyService.addReply(replyDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/reply")
    public ResponseEntity modifyReply(@RequestBody ReplyDto replyDto) {
        log.info(replyDto.toString());
        boolean result = replyService.modifyReply(replyDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/comment/{refId}/reply")
    public ResponseEntity<ReplyListDto> getReplyList(@RequestParam(value="page", required = false) Integer page, @PathVariable String refId) {
        if(page==null) page = 1;

        ReplySelectDto replySelectDto = new ReplySelectDto();
        replySelectDto.setRefId(refId);
        replySelectDto.setOffset(page);

        ReplyListDto result = replyService.getReplyList(replySelectDto);
        result.setPage(page);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
