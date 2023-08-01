package com.szzzing.app.controller;

import com.szzzing.app.domain.User;
import com.szzzing.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping ("/login")
    public void login() {
        System.out.println("로그인");
    }
    
    @GetMapping("/selectAll")
    public void selectAllUser() {
        ArrayList<User> list = userService.selectAll();

        System.out.println(list);
    }
}
