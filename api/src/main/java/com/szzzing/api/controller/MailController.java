package com.szzzing.api.controller;

import com.szzzing.api.dto.common.MailDto;
import com.szzzing.api.dto.mail.CodeDto;
import com.szzzing.api.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Mail controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    /**
     * 이메일 - 인증번호 발송
     *
     * @param mailDto the email dto
     * @return the response entity
     */
    @PostMapping("/email/auth/code")
    public ResponseEntity sendAuthCode(@RequestBody MailDto mailDto) {
        boolean result = mailService.sendAuthCode(mailDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 아이디 찾기 - 아이디 정보 힌트 발송.
     *
     * @param mailDto the email dto
     * @return the response entity
     */
    @PostMapping("/email/find/id")
    public ResponseEntity sendFindId(@RequestBody MailDto mailDto) {
        boolean result = mailService.sendFindId(mailDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 비밀번호 찾기 - 인증번호 발송
     *
     * @param mailDto the email dto
     * @return the response entity
     */
    @PostMapping("/email/find/pw")
    public ResponseEntity sendFindPw(@RequestBody MailDto mailDto) {
        int result = mailService.sendFindPw(mailDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 발송한 인증코드와 입력한 인증코드 비교
     *
     * @param codeDto 입력한 인증번호, 이메일, 타입
     * @return 결과
     */
    @GetMapping("/email/match")
    public ResponseEntity matchCode(@ModelAttribute CodeDto codeDto) {
        boolean result = mailService.matchCode(codeDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
