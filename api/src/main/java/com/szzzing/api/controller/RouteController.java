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

    /**
     * Route 1.
     */
    @GetMapping("/")
    public void route1() {
    }

    /**
     * Route 2.
     */
    @GetMapping("/{path1}")
    public void route2() {
    }

    /**
     * Route 3.
     */
    @GetMapping("/{path1}/{path2}")
    public void route3() {
    }

    /**
     * Route 4.
     */
    @GetMapping("/{path1}/{path2}/{path3}")
    public void route4() {
    }
}
