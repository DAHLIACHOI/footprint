package com.study.footprint.controller.posting;

import com.study.footprint.service.posting.PostingService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostingController {

    private final PostingService postingService;

    @Builder
    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }
}
