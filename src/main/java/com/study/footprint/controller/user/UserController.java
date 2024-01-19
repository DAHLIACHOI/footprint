package com.study.footprint.controller.user;

import com.study.footprint.service.users.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Builder
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
