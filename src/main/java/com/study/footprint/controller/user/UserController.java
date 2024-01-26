package com.study.footprint.controller.user;

import com.study.footprint.dto.user.JoinReqDto;
import com.study.footprint.service.users.UserService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Builder
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/join")
    public ResponseEntity<String> join(@Valid @RequestBody JoinReqDto joinReqDto){

        try {
            userService.join(joinReqDto);

        } catch (Exception e) {
            log.error("회원 가입 실패 : ", e);

        }

        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }
}
