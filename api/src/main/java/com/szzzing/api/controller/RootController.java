package com.szzzing.api.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Paths;

//@CrossOrigin(origins="http://localhost:8081")
@Controller
@Slf4j
public class RootController {
    // context root
    @GetMapping("/")
    public String home(HttpSession session) {
        return "index.html";
    }
}