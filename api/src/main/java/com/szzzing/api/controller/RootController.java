package com.szzzing.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@CrossOrigin(origins="http://localhost:8081")
@Controller
public class RootController {

    // context root
    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    // URL 이동시 수행 - 토큰 검증 및 사용자 정보 업데이트
    @GetMapping("/route")
    public void route(HttpServletRequest request, HttpServletResponse response) {
    }
}