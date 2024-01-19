package com.study.footprint.controller.place;

import com.study.footprint.service.place.PlaceService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @Builder
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
}
