package com.study.footprint.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class EnvRestController {
    private Environment env;
    private ConfigUtil somunaConfigUtil;

    @Builder
    public EnvRestController(Environment env, ConfigUtil somunaConfigUtil) {
        this.env = env;
        this.somunaConfigUtil = somunaConfigUtil;
    }

    @GetMapping("/profile")
    public String getProfile() {
        String currentProfile = Arrays.stream(env.getActiveProfiles())
                .filter(somunaConfigUtil.getSERVICE_PROFILE_LIST()::contains)
                .collect(Collectors.joining());

        log.info("[Current Profile] : " + currentProfile);

        return currentProfile;
    }
}
