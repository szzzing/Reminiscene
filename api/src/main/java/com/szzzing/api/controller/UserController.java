package com.szzzing.api.controller;

import com.szzzing.api.dto.user.*;
import com.szzzing.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

/**
 * The type User controller.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @param userDto the user dto
     * @return the response entity
     */
    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        boolean result = userService.register(userDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 아이디/이메일/닉네임 중복 체크
     *
     * @param userCheckDto 체크할 요소
     * @param request  로그인 유저 정보 - 로그인한 유저가 있는 경우 해당 유저의 아이디/이메일/닉네임은 제외하고 결과를 조회한다.
     * @return 체크 결과
     */
    @GetMapping(value="", params="type=check")
    public ResponseEntity<?> check(@ModelAttribute UserCheckDto userCheckDto, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        boolean result = userService.check(principal, userCheckDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 마이페이지 - 내 정보 수정
     *
     * @param userModifyDto 수정할 정보
     * @param request       사용자 정보
     * @return 수정 결과
     */
    @PutMapping("/{id}")
    public ResponseEntity mypageModify(@ModelAttribute UserModifyDto userModifyDto, HttpServletRequest request) {
        userModifyDto.setId(request.getUserPrincipal().getName());
        boolean result = userService.modifyUser(userModifyDto);
        return new ResponseEntity(result, result ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 전체 유저 조회
     *
     * @param userSelectDto 조회 조건
     * @return the user list
     */
    @GetMapping("")
    public ResponseEntity<UserListDto> getUserList(UserSelectDto userSelectDto) {
        UserListDto result = userService.getUserList(userSelectDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 아이디로 유저 조회
     *
     * @param id 조회할 유저 아이디
     * @return 유저 user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        UserDto result = userService.getUser(id);
        return new ResponseEntity<>(result, result!=null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * 비밀번호 변경
     *
     * @param userModifyDto 변경할 유저 정보
     * @return 변경 결과
     */
    @PutMapping(params="type=pw")
    public ResponseEntity modifyPw(@RequestBody UserModifyDto userModifyDto) {
        boolean result = userService.modifyPw(userModifyDto);
        return new ResponseEntity(result, result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 유저가 입력한 비밀번호와 실제 비밀번호 일치 여부 체크
     *
     * @param pw      입력한 비밀번호
     * @param request 사용자 정보
     * @return 일치 여부
     */
    @GetMapping(params="type=matchPw")
    public ResponseEntity checkUserPw(@RequestParam(value="pw") String pw, HttpServletRequest request) {
        String id = request.getUserPrincipal().getName();
        boolean result = userService.checkUserPw(id, pw);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     *
     * @param id      아이디
     * @param request 사용자 정보
     * @return 탈퇴 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity withdraw(@PathVariable String id, HttpServletRequest request) {
        String loginUser = request.getUserPrincipal().getName();
        // 요청한 유저와 로그인 되어있는 유저가 일치하는 경우
        if(loginUser.equals(id)) {
            boolean result = userService.withdraw(id);
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}