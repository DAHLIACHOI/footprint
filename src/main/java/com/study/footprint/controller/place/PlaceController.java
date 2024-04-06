package com.study.footprint.controller.place;

import com.study.footprint.common.converter.common.CityTypeCd;
import com.study.footprint.common.response.ListResult;
import com.study.footprint.config.ConfigUtil;
import com.study.footprint.dto.place.response.GetPlaceResDto;
import com.study.footprint.service.place.PlaceService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<ListResult<GetPlaceResDto>> getAllPlacesV1() {

        ListResult<GetPlaceResDto> result = placeService.getAllPlacesV1();

        return ResponseEntity.ok(result);
    }

    /**
     * v2) 모든 발자취 조회
     * 사용자가 생성한 모든 발자취 위치 정보 조회
     * @return
     */
    @GetMapping("/v2/places")
    public ResponseEntity<ListResult<GetPlaceResDto>> getAllPlacesV2() {

        Long memberId = configUtil.getLoginUserId();
        ListResult<GetPlaceResDto> result = placeService.getAllPlacesV2(memberId);

        return ResponseEntity.ok(result);
    }

    /**
     * v1) 특정 지역 발자취 조회
     * 기존 버전은 string으로 받았는데, 리팩토링 버전은 enum으로 받음
     */
    @GetMapping("/v1/places/{city}")
    public ResponseEntity<ListResult<GetPlaceResDto>> getPlacesByCityV1(@PathVariable("city") CityTypeCd city) {

        ListResult<GetPlaceResDto> result = placeService.getPlacesByCityV1(city);

        return ResponseEntity.ok(result);
    }
}
