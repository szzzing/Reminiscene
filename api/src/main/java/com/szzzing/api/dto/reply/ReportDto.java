package com.szzzing.api.dto.reply;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReportDto {
    private int id;
    private String userId;
    private int replyId;
    private String content;
    private Timestamp creDate;
    private boolean status;
}
