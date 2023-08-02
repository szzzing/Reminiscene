package com.szzzing.app.domain;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class UserLogin {
    private String id;
    private String pw;
}