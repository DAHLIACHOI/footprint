package com.study.footprint.service.place;

import com.study.footprint.common.converter.CityConverter;
import com.study.footprint.common.converter.common.CityTypeCd;
import com.study.footprint.domain.place.Place;
import com.study.footprint.domain.place.PlaceRepository;
import com.study.footprint.dto.place.request.UploadPlaceReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }


    /**
     * 해당 장소가 있다면 찾아서 리턴하고, 없다면 생성하고 리턴한다.
     * @param uploadPlaceReqDto
     * @return
     */
    public Place getPlace(UploadPlaceReqDto uploadPlaceReqDto) {

        return placeRepository.findByLatitudeAndLongitude(uploadPlaceReqDto.latitude(), uploadPlaceReqDto.longitude())
                .orElseGet(() -> createPlace(uploadPlaceReqDto));
    }


    private Place createPlace(UploadPlaceReqDto uploadPlaceReqDto) {

        Place place = Place.builder()
                .name(uploadPlaceReqDto.name())
                .address(uploadPlaceReqDto.address())
                .latitude(uploadPlaceReqDto.latitude())
                .longitude(uploadPlaceReqDto.longitude())
                .cityTypeCd(CityTypeCd.enumOf(CityConverter.getCityCode(uploadPlaceReqDto.address())))
                .build();

        return placeRepository.save(place);
    }

}
