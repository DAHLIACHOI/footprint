package com.study.footprint.controller.member;

import com.study.footprint.service.member.AuthService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    private final AuthService authService;

    @Builder
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
}
