package com.szzzing.api.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Paths;

@Controller
@Slf4j
public class RootController {
    @GetMapping("/")
    public String home() {
        return "index.html";
    }
}