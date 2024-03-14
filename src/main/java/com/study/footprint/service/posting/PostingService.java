package com.study.footprint.service.posting;

import com.study.footprint.common.exception.CommonNotFoundException;
import com.study.footprint.common.response.SingleResult;
import com.study.footprint.config.s3.S3Service;
import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.member.MemberRepository;
import com.study.footprint.domain.place.Place;
import com.study.footprint.domain.posting.Posting;
import com.study.footprint.domain.posting.PostingRepository;
import com.study.footprint.dto.posting.request.UploadPostingReqDto;
import com.study.footprint.dto.posting.response.UploadPostingResDto;
import com.study.footprint.service.common.ResponseService;
import com.study.footprint.service.place.PlaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Transactional(readOnly = true)
@Service
public class PostingService {

    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;
    private final PlaceService placeService;
    private final ResponseService responseService;
    private final S3Service s3Service;

    public PostingService(PostingRepository postingRepository, MemberRepository memberRepository,
                          PlaceService placeService, ResponseService responseService, S3Service s3Service) {
        this.postingRepository = postingRepository;
        this.memberRepository = memberRepository;
        this.placeService = placeService;
        this.responseService = responseService;
        this.s3Service = s3Service;
    }

    /**
     * v1) 게시물 업로드 (한 트랜잭션 안에서 이미지 업로드)
     * @param uploadPostingReqDto
     * @param file
     * @param userId
     * @return
     */
    @Transactional
    public SingleResult<UploadPostingResDto> uploadPostingV1(UploadPostingReqDto uploadPostingReqDto, MultipartFile file, Long userId) throws IOException {

        Member member = findMemberById(userId);

        Place place = placeService.getPlace(uploadPostingReqDto.uploadPlaceReqDto());

        // 이미지 업로드
        String imageUrl = s3Service.upload(file);

        // 게시물 저장
        Posting posting = Posting.builder()
                .title(uploadPostingReqDto.title())
                .content(uploadPostingReqDto.content())
                .recordDate(uploadPostingReqDto.recordDate())
                .imageUrl(imageUrl)
                .place(place)
                .isPublic(uploadPostingReqDto.isPublic())
                .member(member)
                .build();

        postingRepository.save(posting);

        // 반환값
        UploadPostingResDto uploadPostingResDto = UploadPostingResDto.builder().postingId(posting.getId()).build();

        return responseService.getSingleResult(uploadPostingResDto);

    }


    /**
     * v2) 게시물 업로드 (해당 트랜잭션에서는 이미지 업로드 안함)
     * @param uploadPostingReqDto
     * @param imageUrl
     * @param userId
     * @return
     */
    @Transactional
    public SingleResult<UploadPostingResDto> uploadPostingV2(UploadPostingReqDto uploadPostingReqDto, String imageUrl, Long userId) {

        Member member = findMemberById(userId);

        Place place = placeService.getPlace(uploadPostingReqDto.uploadPlaceReqDto());

        // 게시물 저장
        Posting posting = Posting.builder()
                .title(uploadPostingReqDto.title())
                .content(uploadPostingReqDto.content())
                .recordDate(uploadPostingReqDto.recordDate())
                .imageUrl(imageUrl)
                .place(place)
                .isPublic(uploadPostingReqDto.isPublic())
                .member(member)
                .build();

        postingRepository.save(posting);

        // 반환값
        UploadPostingResDto uploadPostingResDto = UploadPostingResDto.builder().postingId(posting.getId()).build();

        return responseService.getSingleResult(uploadPostingResDto);

    }


    private Member findMemberById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new CommonNotFoundException("userNotFound"));
    }
}
