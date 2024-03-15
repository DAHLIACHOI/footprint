package com.study.footprint.controller.comment;

import com.study.footprint.common.response.SingleResult;
import com.study.footprint.dto.comment.request.UploadCommentReqDto;
import com.study.footprint.dto.comment.response.UploadCommentResDto;
import com.study.footprint.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommentController {

    private final CommentService commentService;

    @Builder
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/v1/comment/{posting-id}")
    public ResponseEntity<SingleResult<UploadCommentResDto>> uploadComment(@PathVariable("posting-id") Long postingId,
                                                                           @Valid @RequestBody UploadCommentReqDto uploadCommentReqDto) {

        SingleResult<UploadCommentResDto> result = commentService.uploadCommentV1(postingId, uploadCommentReqDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
