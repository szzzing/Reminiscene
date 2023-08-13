package com.szzzing.api.security.auth;

import com.google.gson.Gson;
import com.szzzing.api.domain.User;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

public class AuthUtil {

    // 유저 정보를 응답 헤더에 담는 메서드
    public static String userToJson(User user) {

        // 보안을 위해 비밀번호 삭제
        user.setPw(null);
        // 닉네임 ASCII 인코딩
        if(user.getNickname()!=null) {
            user.setNickname(URLEncoder.encode(user.getNickname(), StandardCharsets.UTF_8));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        // json 객체로 변환
        Gson gson = new Gson();
        String json = gson.toJson(user);

        return json;
    }
}
