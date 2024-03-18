package com.study.footprint.controller.posting;

import com.study.footprint.common.response.SingleResult;
import com.study.footprint.config.ConfigUtil;
import com.study.footprint.config.s3.S3Service;
import com.study.footprint.dto.posting.request.UploadPostingReqDto;
import com.study.footprint.dto.posting.response.GetPostingResDto;
import com.study.footprint.dto.posting.response.UploadPostingResDto;
import com.study.footprint.service.posting.PostingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    /**
     * v1) 게시물 업로드 (한 트랜잭션 안에서 이미지 업로드)
     * @param file
     * @param uploadPostingReqDto
     * @return SingleResult<UploadPostingResDto> postingId(게시물 id)
     * @throws IOException
     */
    @PostMapping("/v1/posting")
    public ResponseEntity<SingleResult<UploadPostingResDto>> uploadPosting(@NotNull(message = "requiredFile") @RequestPart MultipartFile file,
                                                                          @Valid @RequestPart UploadPostingReqDto uploadPostingReqDto) throws IOException {

        // 현재 로그인한 유저 정보 가져오기
        Long userId = configUtil.getLoginUserId();

        SingleResult<UploadPostingResDto> result = postingService.uploadPostingV1(uploadPostingReqDto, file, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    /**
     * v2) 게시물 업로드 (해당 서비스 트랜잭션에서는 이미지 업로드 안함)
     * @param file
     * @param uploadPostingReqDto
     * @return SingleResult<UploadPostingResDto> postingId(게시물 id)
     * @throws IOException
     */
    @PostMapping("/v2/posting")
    public ResponseEntity<SingleResult<UploadPostingResDto>> uploadPostingV2(@NotNull(message = "requiredFile") @RequestPart MultipartFile file,
                                                                           @Valid @RequestPart UploadPostingReqDto uploadPostingReqDto) throws IOException {

        // 현재 로그인한 유저 정보 가져오기
        Long userId = configUtil.getLoginUserId();

        // 파일 업로드 후 url 반환 -> 파일 업로드와 포스팅 업로드 트랜잭션 분리
        String imageUrl = s3Service.upload(file);

        SingleResult<UploadPostingResDto> result = postingService.uploadPostingV2(uploadPostingReqDto, imageUrl);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/v3/posting")
    public ResponseEntity<SingleResult<UploadPostingResDto>> uploadPostingV3(@NotNull(message = "requiredFile") @RequestPart MultipartFile file,
                                                                           @Valid @RequestPart UploadPostingReqDto uploadPostingReqDto) throws IOException {

        // 파일 업로드 후 url 반환 -> 파일 업로드와 포스팅 업로드 트랜잭션 분리
        String imageUrl = s3Service.upload(file);

        SingleResult<UploadPostingResDto> result = postingService.uploadPostingV3(uploadPostingReqDto, imageUrl, configUtil.getLoginUserId());

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    /**
     * v1) 게시물 상세 조회
     * @param postingId
     * @return
     */
    @GetMapping("/v1/posting/{posting-id}")
    public ResponseEntity<SingleResult<GetPostingResDto>> getPostingV1(@PathVariable("posting-id") Long postingId) {

        SingleResult<GetPostingResDto> result = postingService.getPostingV1(postingId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
