package com.szzzing.api.controller;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.comment.CommentListDto;
import com.szzzing.api.dto.comment.CommentSelectDto;
import com.szzzing.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommentDto> getComment(@ModelAttribute CommentDto commentDto) {
        CommentDto result = commentService.getComment(commentDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}/comment")
    public ResponseEntity<CommentListDto> getMovieComment(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="sort", required = false) String sort, @PathVariable String movieId) {
        if(page==null) page = 1;

        CommentSelectDto commentSelectDto = new CommentSelectDto();
        commentSelectDto.setMovieId(movieId);
        commentSelectDto.setOffset(page);
        commentSelectDto.setSort(sort);

        CommentListDto result = commentService.getMovieComment(commentSelectDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
