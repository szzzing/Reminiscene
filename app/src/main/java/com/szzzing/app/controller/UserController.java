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

    @GetMapping("/admin")
    public String admin() {
        System.out.println("관리자");
        return "관리자";
    }
}
