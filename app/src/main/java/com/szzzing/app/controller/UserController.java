package com.szzzing.app.controller;

import com.szzzing.app.domain.User;
import com.szzzing.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/selectAll")
    public void selectAllUser() {
        ArrayList<User> list = userService.selectAll();
        System.out.println(list);
    }

    // 로그인 - 로그인시 유저 정보
    @GetMapping("/user/{id}")
    public User loginInfo(@PathVariable String id) {
        return userService.selectOneById(id);
    }

    // 회원가입
    @PostMapping("/register")
    public int register() {
        return 0;
    }

    // 회원가입 - 아이디 중복 체크
    @GetMapping("/user/check/id/{id}")
    public boolean checkAvailableId(@PathVariable String id) {
        return userService.selectOneById(id) == null;
    }

    // 회원가입 - 이메일 중복 체크
    @GetMapping("/user/check/email/{email}")
    public boolean checkAvailableEmail(@PathVariable String email) {
        return userService.selectOneByEmail(email) == null;
    }

    // 회원가입
}
