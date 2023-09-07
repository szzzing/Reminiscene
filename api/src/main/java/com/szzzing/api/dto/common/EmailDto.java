package com.szzzing.api.dto.common;

import lombok.Data;

@Data
public class EmailDto {
    private String to;
    private String subject;
    private String body;
}
