package com.study.footprint.service.member;

import com.study.footprint.common.exception.CommonConflictException;
import com.study.footprint.common.exception.CommonNotFoundException;
import com.study.footprint.common.response.ListResult;
import com.study.footprint.common.response.SingleResult;
import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.member.MemberRepository;
import com.study.footprint.dto.member.request.JoinReqDto;
import com.study.footprint.dto.member.response.TestGetMembersResDto;
import com.study.footprint.dto.member.response.JoinResDto;
import com.study.footprint.service.common.ResponseService;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;

    @Builder
    public MemberService(MemberRepository memberRepository, ResponseService responseService) {
        this.memberRepository = memberRepository;
        this.responseService = responseService;
    }

    @Transactional
    public SingleResult<JoinResDto> join(JoinReqDto joinReqDto) {

        // 이미 가입된 멤버인지 확인
        validExistMember(joinReqDto);

        // 사용가능한 닉네임인지 확인
        validUsingnickname(joinReqDto);

        Member member = Member.builder()
                .email(joinReqDto.getEmail())
                .password(joinReqDto.getPassword())
                .nickname(joinReqDto.getNickname())
                .build();

        memberRepository.save(member);

        JoinResDto joinResDto = JoinResDto.builder()
                .id(member.getId())
                .build();

        return responseService.getSingleResult(joinResDto);

    }

    private void validExistMember(JoinReqDto joinReqDto) {
        if (memberRepository.existsByEmail(joinReqDto.getEmail())) {
            throw new CommonConflictException("duplicationUser");
        }
    }

    private void validUsingnickname(JoinReqDto joinReqDto) {
        if (memberRepository.existsByNickname(joinReqDto.getNickname())) {
            throw new CommonConflictException("duplicationNickname");
        }
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new CommonNotFoundException("userNotFound"));
    }


    /**
     * 테스트 v1) 멤버 조회 (조회할 때 포스팅 개수도 같이 가져오기)
     * @return
     */
    public ListResult<TestGetMembersResDto> testGetMembersV1() {

        List<TestGetMembersResDto> testGetMembersResDtoList = new ArrayList<>();

        List<Member> members = memberRepository.findAll();

        for(Member member : members) {
            TestGetMembersResDto testGetMembersResDto = TestGetMembersResDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .imageUrl(member.getProfileImageUrl())
                    .postingCount((long)member.getPostings().size())
                    .build();
            testGetMembersResDtoList.add(testGetMembersResDto);
        }

        return responseService.getListResult(testGetMembersResDtoList);
    }

    /**
     * 테스트 v2) 멤버 조회 (조회할 때 포스팅 개수도 같이 가져오기)
     * fetch join 사용
     * @return
     */
    public ListResult<TestGetMembersResDto> testGetMembersV2() {

        List<TestGetMembersResDto> getMembersResDtoList = new ArrayList<>();
        List<Member> members = memberRepository.findAllFetch();

        for(Member member : members) {
            TestGetMembersResDto testGetMembersResDto = TestGetMembersResDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .imageUrl(member.getProfileImageUrl())
                    .postingCount((long)member.getPostings().size())
                    .build();
            getMembersResDtoList.add(testGetMembersResDto);
        }

        return responseService.getListResult(getMembersResDtoList);
    }
}
