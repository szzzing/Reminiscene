package com.szzzing.api.controller;

import com.szzzing.api.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @RequestMapping("/email")
    public ResponseEntity SendMail(@RequestBody String email) {
        int result = mailService.sendMail(email.replaceAll("%40", "@").replaceAll("=", ""));

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
