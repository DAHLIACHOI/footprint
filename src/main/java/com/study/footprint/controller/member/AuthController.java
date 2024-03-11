package com.study.footprint.controller.member;

import com.study.footprint.common.response.SingleResult;
import com.study.footprint.dto.member.request.LoginReqDto;
import com.study.footprint.dto.member.request.TokenReqDto;
import com.study.footprint.dto.member.response.LoginResDto;
import com.study.footprint.dto.member.response.TokenResDto;
import com.study.footprint.service.member.AuthService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    private final AuthService authService;

    @Builder
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/v1/login")
    public SingleResult<LoginResDto> login(@RequestBody LoginReqDto loginReqDto) {
        return authService.login(loginReqDto);
    }

    @PostMapping("/v1/reissue")
    public SingleResult<TokenResDto> reissue(@RequestBody TokenReqDto tokenReqDto) {
        return authService.reissue(tokenReqDto);
    }
}
