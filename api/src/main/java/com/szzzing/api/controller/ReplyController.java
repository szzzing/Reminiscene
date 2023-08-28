package com.szzzing.api.controller;

import com.szzzing.api.dto.reply.ReplyDto;
import com.szzzing.api.dto.reply.ReplyListDto;
import com.szzzing.api.dto.reply.ReplySelectDto;
import com.szzzing.api.dto.reply.ReportDto;
import com.szzzing.api.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jdk.javadoc.doclet.Reporter;
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
        boolean result = replyService.modifyReply(replyDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/reply/{id}")
    public ResponseEntity deleteReply(@PathVariable int id) {
        boolean result = replyService.deleteReply(id);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/reply")
    public ResponseEntity<ReplyListDto> getReplyList(@ModelAttribute ReplySelectDto replySelectDto) {
        if(replySelectDto.getPage()==null) replySelectDto.setPage(1);
        replySelectDto.setOffset();

        ReplyListDto result = replyService.getReplyList(replySelectDto);
        result.setPage(replySelectDto.getPage());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    // 신고
    @PostMapping("/report")
    public ResponseEntity addReport(@RequestBody ReportDto reportDto, HttpServletRequest request) {
        String userId = request.getUserPrincipal().getName();
        reportDto.setUserId(userId);
        boolean result = replyService.addReport(reportDto);
        return new ResponseEntity<>(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
