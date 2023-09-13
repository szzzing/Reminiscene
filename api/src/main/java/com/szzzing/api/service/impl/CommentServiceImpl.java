package com.szzzing.api.service.impl;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.comment.CommentListDto;
import com.szzzing.api.dto.comment.CommentSelectDto;
import com.szzzing.api.dto.comment.LikeDto;
import com.szzzing.api.repository.CommentRepository;
import com.szzzing.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("commentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public boolean addComment(CommentDto commentDto) {
        return commentRepository.insertOneComment(commentDto) > 0;
    }

    @Override
    public CommentDto getComment(CommentSelectDto commentSelectDto) {
        return commentRepository.selectOneComment(commentSelectDto);
    }

    @Override
    public boolean modifyComment(CommentDto commentDto) {
        return commentRepository.updateOneComment(commentDto) > 0;
    }

    @Override
    public CommentListDto getCommentList(CommentSelectDto commentSelectDto) {
        if(commentSelectDto.getPage()==null) commentSelectDto.setPage(1);
        commentSelectDto.setOffset();

        CommentListDto result = new CommentListDto();
        result.setListCount(commentRepository.selectCommentListCount(commentSelectDto));
        result.setList(commentRepository.selectCommentList(commentSelectDto));
        result.setPage(commentSelectDto.getPage());
        return result;
    }

    @Override
    public boolean addLike(LikeDto likeDto) {
        return commentRepository.insertOneLike(likeDto) > 0;
    }

    @Override
    public boolean deleteLike(LikeDto likeDto) {
        return commentRepository.deleteOneLike(likeDto) > 0;
    }

    @Override
    public CommentListDto getLikeCommentList(CommentSelectDto commentSelectDto) {
        if(commentSelectDto.getPage()==null) commentSelectDto.setPage(1);
        commentSelectDto.setOffset();

        CommentListDto result = new CommentListDto();
        result.setList(commentRepository.selectLikeList(commentSelectDto));
        result.setPage(commentSelectDto.getPage());
        return result;
    }

    @Override
    public int getCommentCount() {
        return commentRepository.selectCommentCount();
    }

}
