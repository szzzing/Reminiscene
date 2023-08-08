package com.szzzing.app.security.auth;

import com.google.gson.Gson;
import com.szzzing.app.domain.User;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class AuthUtil {

    // 유저 정보를 응답 헤더에 담는 메서드
    public static void setUserHeader(HttpServletResponse response, User user) {

        // 보안을 위해 비밀번호 삭제
        user.setPw(null);
        // 닉네임 ASCII 인코딩
        if(user.getNickname()!=null) {
            user.setNickname(URLEncoder.encode(user.getNickname(), StandardCharsets.UTF_8));
        }

        // json 객체로 변환
        Gson gson = new Gson();
        String json = gson.toJson(user);
        response.addHeader("user", json);
    }
}
