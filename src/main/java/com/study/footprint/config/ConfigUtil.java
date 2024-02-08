package com.study.footprint.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class ConfigUtil {
    private final List<String> SERVICE_PROFILE_LIST = new ArrayList<>(Arrays.asList("local-1", "local-2", "dev-1", "dev-2"));
}
