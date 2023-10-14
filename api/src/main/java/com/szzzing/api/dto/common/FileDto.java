package com.szzzing.api.dto.common;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FileDto {
    private String originalName;
    private String renameName;
    private String directory;
    private Timestamp creDate;
    private long size;
}
