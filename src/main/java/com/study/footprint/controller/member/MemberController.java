package com.study.footprint.controller.member;

import com.study.footprint.common.response.ListResult;
import com.study.footprint.common.response.SingleResult;
import com.study.footprint.dto.member.request.JoinReqDto;
import com.study.footprint.dto.member.response.TestGetMembersResDto;
import com.study.footprint.dto.member.response.JoinResDto;
import com.study.footprint.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Builder
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/v1/join")
    public ResponseEntity<SingleResult<JoinResDto>> join(@Valid @RequestBody JoinReqDto joinReqDto) {

        // 비밀번호 암호화
        joinReqDto.encodePassword(passwordEncoder, joinReqDto.getPassword());

        SingleResult<JoinResDto> result = memberService.join(joinReqDto);

        //비밀번호 초기화 (보안상 목적)
        joinReqDto.deletePassword();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 테스트 v1) 멤버 조회 (조회할 때 포스팅 개수도 같이 가져오기)
     */
    @GetMapping("/test/v1/members")
    public ResponseEntity<ListResult<TestGetMembersResDto>> testGetMembersV1() {

        ListResult<TestGetMembersResDto> result = memberService.testGetMembersV1();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 테스트 v2) 멤버 조회 (조회할 때 포스팅 개수도 같이 가져오기)
     * fetch join 사용
     */
    @GetMapping("/test/v2/members")
    public ResponseEntity<ListResult<TestGetMembersResDto>> testGetMembersV2() {

        ListResult<TestGetMembersResDto> result = memberService.testGetMembersV2();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
