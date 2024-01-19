package com.study.footprint.controller.like;

import com.study.footprint.service.like.LikeService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LikeController {

    private final LikeService likeService;

    @Builder
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
}
