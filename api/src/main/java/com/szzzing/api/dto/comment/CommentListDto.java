package com.szzzing.api.dto.comment;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CommentListDto {
    private ArrayList<CommentDto> list;
    private int page;
}
