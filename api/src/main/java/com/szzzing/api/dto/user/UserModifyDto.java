package com.szzzing.api.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
public class UserModifyDto {
    private String id;
    private String pw;
    private String email;
    private String nickname;
    private String gender;
    private String birthday;    // Form Data 이슈로 Timestamp x
    private String originalImage;
    private String newImage;
    private MultipartFile uploadFile;
}
