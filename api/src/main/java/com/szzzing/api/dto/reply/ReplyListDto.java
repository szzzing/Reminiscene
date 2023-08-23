package com.szzzing.api.dto.reply;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ReplyListDto {
    private ArrayList<ReplyDto> list;
    private int page;
}
