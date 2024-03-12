package com.study.footprint.common.util.validation;

import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ValidationUtil {

    public static boolean hasFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }
}
