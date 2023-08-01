package com.szzzing.app.controller;

import com.szzzing.app.domain.User;
import com.szzzing.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping ("/login")
    public void login(@RequestBody User user) {
        System.out.println("로그인: "+user.getId()+"/"+user.getPw());
    }
    
    @GetMapping("/selectAll")
    public void selectAllUser() {
        ArrayList<User> list = userService.selectAll();

        System.out.println(list);
    }
}
