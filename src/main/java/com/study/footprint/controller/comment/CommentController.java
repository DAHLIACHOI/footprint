package com.study.footprint.controller.comment;

import com.study.footprint.service.comment.CommentService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommentController {

    private final CommentService commentService;

    @Builder
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

}
