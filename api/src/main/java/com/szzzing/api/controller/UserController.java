package com.szzzing.api.controller;

import com.szzzing.api.dto.UserDto;
import com.szzzing.api.dto.UserModifyDto;
import com.szzzing.api.service.UserService;
import com.szzzing.api.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admin/selectAll")
    public void selectAllUser() {
        ArrayList<UserDto> list = userService.selectAll();
        System.out.println(list);
    }

    // 회원가입
    @PostMapping("/auth/register")
    public int register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
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

    // 마이페이지 - 내 정보 수정
    @PostMapping("/mypage/modify")
    public boolean mypageModify(@ModelAttribute UserModifyDto userModifyDto, HttpServletRequest request) {
        userModifyDto.setId(request.getUserPrincipal().getName());
        return userService.mypageModify(userModifyDto);
    }

    // 마이페이지 - 내 정보 수정 - 닉네임 중복 체크
    @GetMapping("/mypage/check/nickname/{nickname}")
    public boolean mypageCheckNickname(@PathVariable String nickname) {
        return userService.mypageCheckNickname(nickname);
    }

    // 마이페지이 - 내 정보 수정 - 이메일 중복 체크
    @GetMapping("/mypage/check/email/{email}")
    public boolean mypageCheckEmail(@PathVariable String email) {
        return userService.mypageCheckEmail(email);
    }
}
