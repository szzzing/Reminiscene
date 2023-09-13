package com.szzzing.api.controller;

import com.szzzing.api.dto.common.EmailDto;
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

    // 회원가입 - 인증번호 발송
    @RequestMapping("/email/auth/code")
    public ResponseEntity sendAuthCode(@RequestBody EmailDto emailDto) {
        int result = mailService.sendAuthCode(emailDto);

        return new ResponseEntity(result, HttpStatus.OK);
    }
    // 아이디 찾기 - 아이디 정보 힌트 발송
    @RequestMapping("/email/find/id")
    public ResponseEntity sendFindId(@RequestBody EmailDto emailDto) {
        boolean result = mailService.sendFindId(emailDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }
    // 비밀번호 찾기 - 인증번호 발송
    @RequestMapping("/email/find/pw")
    public ResponseEntity sendFindPw(@RequestBody EmailDto emailDto) {
        int result = mailService.sendFindPw(emailDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
