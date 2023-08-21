package com.szzzing.api.controller;

import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.dto.user.UserModifyDto;
import com.szzzing.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        boolean result = userService.register(userDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 아이디/이메일 중복 체크
    @GetMapping("")
    public ResponseEntity check(@RequestParam(value="id", required=false) String id, @RequestParam(value="email", required=false) String email, @RequestParam(value="nickname", required=false) String nickname, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        boolean result = userService.check(principal, id, email, nickname);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 마이페이지 - 내 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity mypageModify(@ModelAttribute UserModifyDto userModifyDto, HttpServletRequest request) {
        userModifyDto.setId(request.getUserPrincipal().getName());
        boolean result = userService.mypageModify(userModifyDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}