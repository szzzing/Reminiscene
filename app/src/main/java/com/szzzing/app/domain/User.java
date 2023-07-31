package com.szzzing.app.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.sql.Date;


@Data
public class User {
    private String id;
    private String pw;
    private String email;
    private String nickname;
    private char gender;
    private Date birthday;
    private Timestamp regDate;
    private Timestamp modDate;
    private char role;
}