package com.szzzing.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins="http://localhost:8081")
@Controller
public class RootController {

    // context root
    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    // url 이동 관련 인증
    @GetMapping("/route")
    public void route(HttpServletRequest request, HttpServletResponse response) {
    }
}