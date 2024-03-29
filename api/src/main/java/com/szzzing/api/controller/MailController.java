package com.szzzing.api.controller;

import com.szzzing.api.dto.mail.MailDto;
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
    @PostMapping("/email/code")
    public ResponseEntity<Boolean> sendAuthCode(@RequestBody MailDto mailDto) {
        boolean result = mailService.sendAuthCode(mailDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 아이디 찾기 - 아이디 정보 힌트 발송.
     *
     * @param mailDto the email dto
     * @return the response entity
     */
    @PostMapping("/email/id")
    public ResponseEntity<Boolean> sendFindId(@RequestBody MailDto mailDto) {
        boolean result = mailService.sendFindId(mailDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 비밀번호 찾기 - 인증번호 발송
     *
     * @param mailDto the email dto
     * @return the response entity
     */
    @PostMapping("/email/pw")
    public ResponseEntity<Boolean> sendFindPw(@RequestBody MailDto mailDto) {
        boolean result = mailService.sendFindPw(mailDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 발송한 인증코드와 입력한 인증코드 비교
     *
     * @param codeDto 입력한 인증번호, 이메일, 타입
     * @return 결과
     */
    @GetMapping("/email/match")
    public ResponseEntity<Boolean> matchCode(@ModelAttribute CodeDto codeDto) {
        boolean result = mailService.matchCode(codeDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
