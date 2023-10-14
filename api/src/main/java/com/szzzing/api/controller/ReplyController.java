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

/**
 * The type Reply controller.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 댓글 추가
     *
     * @param replyDto 댓글 정보
     * @return 추가 결과
     */
    @PostMapping("/reply")
    public ResponseEntity addReply(@RequestBody ReplyDto replyDto) {
        boolean result = replyService.addReply(replyDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 댓글 수정
     *
     * @param replyDto 수정할 댓글 정보
     * @return 수정 결과
     */
    @PutMapping("/reply")
    public ResponseEntity modifyReply(@RequestBody ReplyDto replyDto) {
        boolean result = replyService.modifyReply(replyDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 댓글 삭제
     *
     * @param id 삭제할 댓글 코드
     * @return 삭제 결과
     */
    @DeleteMapping("/reply/{id}")
    public ResponseEntity deleteReply(@PathVariable int id) {
        boolean result = replyService.deleteReply(id);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 코멘트 전체 댓글 불러오기
     *
     * @param replySelectDto 코멘트 정보, 불러올 댓글 페이지 정보
     * @return 전체 댓글 리스트
     */
    @GetMapping("/reply")
    public ResponseEntity<ReplyListDto> getReplyList(@ModelAttribute ReplySelectDto replySelectDto) {
        if(replySelectDto.getPage()==null) replySelectDto.setPage(1);
        replySelectDto.setOffset();

        ReplyListDto result = replyService.getReplyList(replySelectDto);
        result.setPage(replySelectDto.getPage());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * 댓글 신고 추가
     *
     * @param reportDto 신고 정보
     * @param request   신고자 정보
     * @return 신고 결과
     */
    @PostMapping("/report")
    public ResponseEntity addReport(@RequestBody ReportDto reportDto, HttpServletRequest request) {
        String userId = request.getUserPrincipal().getName();
        reportDto.setUserId(userId);
        boolean result = replyService.addReport(reportDto);
        return new ResponseEntity<>(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
