package com.szzzing.api.dto.reply;

import lombok.Data;

import java.sql.Date;

@Data
public class ReplyDto {
    private int id;
    private String userId;
    private String nickname;
    private String profileImage;
    private boolean userEnable;
    private int refId;
    private String content;
    private Date creDate;
    private Date modDate;
}