package com.szzzing.api.controller;

import com.szzzing.api.exception.message.StatusMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public String error(HttpServletResponse response) {
        int status = response.getStatus();

        if(status==HttpStatus.NOT_FOUND.value()) {
            response.setStatus(HttpStatus.OK.value());
        }
        return "index.html";
    }
}
