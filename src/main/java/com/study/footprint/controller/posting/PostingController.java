package com.study.footprint.controller.posting;

import com.study.footprint.common.response.SingleResult;
import com.study.footprint.config.ConfigUtil;
import com.study.footprint.config.s3.S3Service;
import com.study.footprint.dto.posting.request.UploadPostingReqDto;
import com.study.footprint.dto.posting.response.UploadPostingResDto;
import com.study.footprint.service.posting.PostingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
public class PostingController {

    private final PostingService postingService;
    private final ConfigUtil configUtil;
    private final S3Service s3Service;

    @Builder
    public PostingController(PostingService postingService, ConfigUtil configUtil, S3Service s3Service) {
        this.postingService = postingService;
        this.configUtil = configUtil;
        this.s3Service = s3Service;
    }

    @PostMapping("/v1/posting")
    public ResponseEntity<SingleResult<UploadPostingResDto>> uploadPosting(@NotNull(message = "requiredFile") @RequestPart MultipartFile file,
                                                                           @Valid @RequestPart UploadPostingReqDto uploadPostingReqDto) throws IOException {

        // 현재 로그인한 유저 정보 가져오기
        Long userId = configUtil.getLoginUserId();

        // 파일 업로드 후 url 반환 -> 파일 업로드와 포스팅 업로드 트랜잭션 분리
        String imageUrl = s3Service.upload(file);

        SingleResult<UploadPostingResDto> result = postingService.uploadPosting(uploadPostingReqDto, imageUrl, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
