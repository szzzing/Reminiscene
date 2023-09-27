package com.szzzing.api.controller;

import org.springframework.web.bind.annotation.*;

/**
 * URL 이동시 수행
 * 1. 토큰 검증
 * 2. 사용자 정보 업데이트
 */
@RequestMapping("/route")
@RestController
public class RouteController {

    @GetMapping("/")
    public void route1() {}

    @GetMapping("/{path1}")
    public void route2() {}
}
