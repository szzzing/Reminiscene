package com.szzzing.api.controller;

import com.szzzing.api.domain.User;
import com.szzzing.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admin/selectAll")
    public void selectAllUser() {
        ArrayList<User> list = userService.selectAll();
        System.out.println(list);
    }

    // 회원가입
    @PostMapping("/auth/register")
    public int register(@RequestBody User user) {
        return userService.register(user);
    }

    // 회원가입 - 아이디 중복 체크
    @GetMapping("/auth/check/id/{id}")
    public boolean authCheckId(@PathVariable String id) {
        return userService.authCheckId(id);
    }

    // 회원가입 - 이메일 중복 체크
    @GetMapping("/auth/check/email/{email}")
    public boolean authCheckEmail(@PathVariable String email) {
        return userService.authCheckEmail(email);
    }

    // 마이페이지

    // 마이페이지 - 닉네임 중복 체크
    @GetMapping("/mypage/check/nickname/{nickname}")
    public boolean mypageCheckNickname(@PathVariable String nickname) {
        return userService.mypageCheckNickname(nickname);
    }

    @GetMapping("/mypage/check/email/{email}")
    public boolean mypageCheckEmail(@PathVariable String email) {
        return userService.mypageCheckEmail(email);
    }
}
