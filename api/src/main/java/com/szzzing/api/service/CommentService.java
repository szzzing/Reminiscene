package com.szzzing.api.service;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.comment.CommentListDto;
import com.szzzing.api.dto.comment.CommentSelectDto;
import com.szzzing.api.dto.comment.LikeDto;
import com.szzzing.api.dto.movie.RateDto;
import com.szzzing.api.dto.movie.StatusDto;
import com.szzzing.api.dto.movie.WatchingDto;
import com.szzzing.api.dto.movie.WishDto;
import com.szzzing.api.repository.CommentRepository;
import com.szzzing.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

public interface CommentService {

    public boolean addComment(CommentDto commentDto);

    public CommentDto getComment(CommentSelectDto commentSelectDto);

    public boolean modifyComment(CommentDto commentDto);

    public CommentListDto getCommentList(CommentSelectDto commentSelectDto);

    public boolean addLike(LikeDto likeDto);

    public boolean deleteLike(LikeDto likeDto);

    public CommentListDto getLikeCommentList(CommentSelectDto commentSelectDto);

    int getCommentCount();
}
