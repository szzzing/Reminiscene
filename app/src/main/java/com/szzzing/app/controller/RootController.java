package com.szzzing.app.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins="http://localhost:8081")
@Controller
public class RootController {
    @GetMapping("/")
    public String home() {
        return "index.html";
    }
}