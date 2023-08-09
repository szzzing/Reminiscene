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

    // 로그인 - 로그인시 유저 정보
    @GetMapping("/mypage/{id}")
    public User loginInfo(@PathVariable String id) {
        return userService.selectOneById(id);
    }

    // 회원가입
    @PostMapping("/auth/register")
    public int register(@RequestBody User user) {
        return userService.register(user);
    }

    // 회원가입 - 아이디 중복 체크
    @GetMapping("/auth/check/id/{id}")
    public boolean checkAvailableId(@PathVariable String id) {
        return userService.selectOneById(id) == null;
    }

    // 회원가입 - 이메일 중복 체크
    @GetMapping("/auth/check/email/{email}")
    public boolean checkAvailableEmail(@PathVariable String email) {
        return userService.selectOneByEmail(email) == null;
    }

    // 로그아웃
}
