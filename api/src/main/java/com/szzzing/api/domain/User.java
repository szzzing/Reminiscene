package com.szzzing.api.domain;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class User {
    private String id;
    private String pw;
    private String email;
    private String nickname;
    private String gender;
    private Timestamp birthday;
    private Timestamp regDate;
    private Timestamp modDate;
    private String role;
    private boolean enable;
    private String type;
    private String profileImage;
}