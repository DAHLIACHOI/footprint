package com.study.footprint.controller.like;

import com.study.footprint.common.response.SingleResult;
import com.study.footprint.config.ConfigUtil;
import com.study.footprint.dto.like.response.ClickLikeResDto;
import com.study.footprint.dto.like.response.CountLikeResDto;
import com.study.footprint.service.like.LikeService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LikeController {

    private final LikeService likeService;
    private final ConfigUtil configUtil;

    @Builder
    public LikeController(LikeService likeService, ConfigUtil configUtil) {
        this.likeService = likeService;
        this.configUtil = configUtil;
    }

    /**
     * v1 ) 좋아요가 안 눌려있으면 취소, 눌려있다면 등록
     * @param postingId
     * @return SingleResult<ClickLikeResDto> isLike(좋아요 상태) (true, false)
     */
    @PostMapping("/v1/like/{posting-id}")
    public ResponseEntity<SingleResult<ClickLikeResDto>> clickLike(@PathVariable("posting-id") Long postingId) {

        Long userId = configUtil.getLoginUserId();

        SingleResult<ClickLikeResDto> result = likeService.clickLike(postingId, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * v1 ) 게시물의 좋아요 개수 (개수 테스트용 api)
     * @param postingId
     * @return
     */

    @GetMapping("/v1/like/{posting-id}")
    public ResponseEntity<SingleResult<CountLikeResDto>> countLike(@PathVariable("posting-id") Long postingId) {

        SingleResult<CountLikeResDto> result = likeService.countLike(postingId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * v2 ) 좋아요가 안 눌려있으면 등록, 눌려있다면 취소
     * @param postingId
     * @return SingleResult<ClickLikeResDto> isLike(좋아요 상태) (true, false)
     */
    @PostMapping("/v2/like/{posting-id}")
    public ResponseEntity<SingleResult<ClickLikeResDto>> clickLikeV2(@PathVariable("posting-id") Long postingId) {

        Long userId = configUtil.getLoginUserId();

        SingleResult<ClickLikeResDto> result = likeService.clickLikeV2(postingId, userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 테스트를 위한 api (좋아요 수 누적시키기 위함)
     */
    @PostMapping("/test/like/{posting-id}")
    public ResponseEntity<HttpStatus> testClickLike(@PathVariable("posting-id") Long postingId) {

        Long userId = configUtil.getLoginUserId();

        likeService.testClickLike(postingId, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
