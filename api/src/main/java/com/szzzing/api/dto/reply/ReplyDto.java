package com.szzzing.api.dto.reply;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class ReplyDto {
    private int id;
    private String userId;
    private String nickname;
    private String profileImage;
    private boolean userEnable;
    private int refId;
    private String content;
    private Timestamp creDate;
    private Timestamp modDate;
}