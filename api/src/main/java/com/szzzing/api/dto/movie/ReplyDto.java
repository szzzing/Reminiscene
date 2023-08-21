package com.szzzing.api.dto.movie;

import lombok.Data;

import java.sql.Date;

@Data
public class ReplyDto {
    private int id;
    private String writer;
    private String nickname;
    private int refId;
    private Date creDate;
    private Date modDate;
}