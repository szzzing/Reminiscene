package com.szzzing.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@CrossOrigin(origins="http://localhost:8081")
@Controller
@Slf4j
public class RootController {

    // context root
    @GetMapping("/")
    public String home() {
        return "index.html";
    }
}