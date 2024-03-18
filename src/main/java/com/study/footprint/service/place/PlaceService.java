package com.study.footprint.service.place;

import com.study.footprint.common.converter.CityConverter;
import com.study.footprint.common.converter.common.CityTypeCd;
import com.study.footprint.common.exception.CommonNotFoundException;
import com.study.footprint.common.response.ListResult;
import com.study.footprint.config.ConfigUtil;
import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.member.MemberRepository;
import com.study.footprint.domain.place.Place;
import com.study.footprint.domain.place.PlaceRepository;
import com.study.footprint.domain.posting.Posting;
import com.study.footprint.domain.posting.PostingRepository;
import com.study.footprint.dto.place.request.UploadPlaceReqDto;
import com.study.footprint.dto.place.response.GetAllPlaceResDto;
import com.study.footprint.service.common.ResponseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final PostingRepository postingRepository;
    private final ConfigUtil configUtil;
    private final ResponseService responseService;

    public PlaceService(PlaceRepository placeRepository, MemberRepository memberRepository, PostingRepository postingRepository,
                        ConfigUtil configUtil, ResponseService responseService) {
        this.placeRepository = placeRepository;
        this.memberRepository = memberRepository;
        this.postingRepository = postingRepository;
        this.configUtil = configUtil;
        this.responseService = responseService;
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

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonNotFoundException("userNotFound"));
    }

    /**
     * v1) 모든 발자취 조회
     * @return
     */
    public ListResult<GetAllPlaceResDto> getAllPlacesV1() {

        // 현재 로그인 한 멤버 정보 조회
        Member member = findMemberById(configUtil.getLoginUserId());

        // 멤버의 게시글 조회 (여기서 위치 정보까지 fetch join으로 가져오기)
        List<Posting> postings = postingRepository.findAllByMemberFetch(member);

        Set<GetAllPlaceResDto> getAllPlaceResDtoList = new HashSet<>();

        for (Posting posting : postings) {
            getAllPlaceResDtoList.add(GetAllPlaceResDto.builder()
                    .placeId(posting.getPlace().getId())
                    .placeName(posting.getPlace().getName())
                    .latitude(posting.getPlace().getLatitude())
                    .longitude(posting.getPlace().getLongitude())
                    .build());
        }

        return responseService.getListResult(new ArrayList<>(getAllPlaceResDtoList));
    }

}
