package com.szzzing.api.security.auth;

import com.google.gson.Gson;
import com.szzzing.api.dto.user.UserDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthUtil {

    // 유저 정보를 응답 헤더에 담는 메서드
    public static String userToJson(UserDto userDto) {

        // 보안을 위해 비밀번호 삭제
        userDto.setPw(null);
        // 닉네임 ASCII 인코딩
        if(userDto.getNickname()!=null) {
            userDto.setNickname(URLEncoder.encode(userDto.getNickname(), StandardCharsets.UTF_8));
        }

        // json 객체로 변환
        Gson gson = new Gson();
        String json = gson.toJson(userDto);

        return json;
    }
}
