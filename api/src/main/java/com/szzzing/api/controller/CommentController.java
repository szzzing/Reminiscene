package com.szzzing.api.controller;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.comment.CommentListDto;
import com.szzzing.api.dto.comment.CommentSelectDto;
import com.szzzing.api.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 코멘트
    @PostMapping("/comment")
    public ResponseEntity addComment(@RequestBody CommentDto commentDto) {
        boolean result = commentService.addComment(commentDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/comment")
    public ResponseEntity modifyComment(@RequestBody CommentDto commentDto) {
        boolean result = commentService.modifyComment(commentDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/comment")
    public ResponseEntity<CommentDto> getComment(@ModelAttribute CommentSelectDto commentSelectDto) {
        log.info(commentSelectDto.toString());
        CommentDto result = commentService.getComment(commentSelectDto);
        return new ResponseEntity<>(result, result!=null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/movie/{movieId}/comment")
    public ResponseEntity<CommentListDto> getMovieComment(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="sort", required = false) String sort, @PathVariable String movieId, HttpServletRequest request) {
        String loginUser = null;
        if(request.getUserPrincipal()!=null) {
            loginUser = request.getUserPrincipal().getName();
        }

        if(page==null) page = 1;

        CommentSelectDto commentSelectDto = new CommentSelectDto();
        commentSelectDto.setMovieId(movieId);
        commentSelectDto.setOffset(page);
        commentSelectDto.setSort(sort);
        commentSelectDto.setLoginUser(loginUser);

        CommentListDto result = commentService.getMovieComment(commentSelectDto);
        result.setPage(page);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
