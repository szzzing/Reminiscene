package com.szzzing.api.service;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.comment.CommentListDto;
import com.szzzing.api.dto.comment.CommentSelectDto;
import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
import com.szzzing.api.dto.movie.WatchingDto;
import com.szzzing.api.dto.movie.WishDto;
import com.szzzing.api.repository.CommentRepository;
import com.szzzing.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public boolean addComment(CommentDto commentDto) {
        return commentRepository.insertOneComment(commentDto) > 0;
    }

    public CommentDto getComment(CommentDto commentDto) {
        return commentRepository.selectOneComment(commentDto);
    }

    public boolean modifyComment(CommentDto commentDto) {
        return commentRepository.updateOneComment(commentDto) > 0;
    }

    public CommentListDto getMovieComment(CommentSelectDto commentSelectDto) {
        CommentListDto result = new CommentListDto();
        result.setList(commentRepository.selectCommentList(commentSelectDto));
        return result;
    }
}
