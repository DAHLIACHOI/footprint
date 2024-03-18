package com.study.footprint.controller.place;

import com.study.footprint.common.response.ListResult;
import com.study.footprint.config.ConfigUtil;
import com.study.footprint.dto.place.response.GetAllPlaceResDto;
import com.study.footprint.service.place.PlaceService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PlaceController {

    private final PlaceService placeService;
    private final ConfigUtil configUtil;

    @Builder
    public PlaceController(PlaceService placeService, ConfigUtil configUtil) {
        this.placeService = placeService;
        this.configUtil = configUtil;
    }


    /**
     * v1) 모든 발자취 조회
     * 사용자가 생성한 모든 발자취 위치 정보 조회
     * @return
     */
    @GetMapping("/v1/places")
    public ResponseEntity<ListResult<GetAllPlaceResDto>> getAllPlacesV1() {

        ListResult<GetAllPlaceResDto> result = placeService.getAllPlacesV1();

        return ResponseEntity.ok(result);
    }

    /**
     * v2) 모든 발자취 조회
     * 사용자가 생성한 모든 발자취 위치 정보 조회
     * @return
     */
    @GetMapping("/v2/places")
    public ResponseEntity<ListResult<GetAllPlaceResDto>> getAllPlacesV2() {

        Long memberId = configUtil.getLoginUserId();
        ListResult<GetAllPlaceResDto> result = placeService.getAllPlacesV2(memberId);

        return ResponseEntity.ok(result);
    }
}
