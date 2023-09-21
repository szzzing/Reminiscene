package com.szzzing.api.dto.common;

import lombok.Data;

@Data
public class MailDto {
    private String to;
    private String subject;
    private String body;
    private String id;
}
