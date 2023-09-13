package com.szzzing.api.controller;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.comment.CommentListDto;
import com.szzzing.api.dto.comment.CommentSelectDto;
import com.szzzing.api.dto.comment.LikeDto;
import com.szzzing.api.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * The type Comment controller.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 코멘트 작성
     *
     * @param commentDto the comment dto
     * @return the response entity
     */
    @PostMapping("/comment")
    public ResponseEntity addComment(@RequestBody CommentDto commentDto) {
        boolean result = commentService.addComment(commentDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 코멘트 수정
     *
     * @param commentDto the comment dto
     * @return the response entity
     */
    @PutMapping("/comment")
    public ResponseEntity modifyComment(@RequestBody CommentDto commentDto) {
        boolean result = commentService.modifyComment(commentDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 코멘트 1개 조회
     *
     * @param commentSelectDto the comment select dto
     * @param request          the request
     * @return the comment
     */
    @GetMapping("/comment")
    public ResponseEntity<CommentDto> getComment(@ModelAttribute CommentSelectDto commentSelectDto, HttpServletRequest request) {
        String loginUser  = request.getUserPrincipal()==null ? null : request.getUserPrincipal().getName();
        commentSelectDto.setLoginUser(loginUser);

        CommentDto result = commentService.getComment(commentSelectDto);
        return new ResponseEntity<>(result, result!=null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * 코멘트 리스트 조회
     *
     * @param commentSelectDto the comment select dto
     * @param request          the request
     * @return the comment list
     */
    @GetMapping("/comments")
    public ResponseEntity<CommentListDto> getCommentList(@ModelAttribute CommentSelectDto commentSelectDto, HttpServletRequest request) {
        String loginUser  = request.getUserPrincipal()==null ? null : request.getUserPrincipal().getName();
        commentSelectDto.setLoginUser(loginUser);

        CommentListDto result = commentService.getCommentList(commentSelectDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 좋아요 추가
     *
     * @param likeDto the like dto
     * @return the response entity
     */
    @PostMapping("/like")
    public ResponseEntity addLike(@RequestBody LikeDto likeDto) {
        boolean result = commentService.addLike(likeDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 좋아요 삭제
     *
     * @param commentId the comment id
     * @param request   the request
     * @return the response entity
     */
    @DeleteMapping ("/like/{commentId}")
    public ResponseEntity deleteLike(@PathVariable String commentId, HttpServletRequest request) {
        LikeDto likeDto = new LikeDto();
        String userId = request.getUserPrincipal().getName();
        likeDto.setCommentId(Integer.valueOf(commentId));
        likeDto.setUserId(userId);

        boolean result = commentService.deleteLike(likeDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 좋아요를 누른 코멘트 리스트 조회
     *
     * @param commentSelectDto the comment select dto
     * @return the like comment list
     */
    @GetMapping("/like")
    public ResponseEntity getLikeCommentList(@ModelAttribute CommentSelectDto commentSelectDto) {
        CommentListDto commentListDto = commentService.getLikeCommentList(commentSelectDto);
        return new ResponseEntity(commentListDto, HttpStatus.OK);
    }

    /**
     * 코멘트 개수 조회
     *
     * @return the comment count
     */
    @GetMapping("/comment/count")
    public ResponseEntity getCommentCount() {
        int result = commentService.getCommentCount();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
