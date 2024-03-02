package com.study.footprint.controller.member;

import com.study.footprint.common.response.SingleResult;
import com.study.footprint.dto.member.JoinReqDto;
import com.study.footprint.dto.member.JoinResDto;
import com.study.footprint.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        SingleResult<JoinResDto> result = null;

//        if (errors.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        // 비밀번호 암호화
        joinReqDto.encodePassword(passwordEncoder, joinReqDto.getPassword());

        try {
            result = memberService.join(joinReqDto);

        } catch (Exception e) {
            log.error("회원 가입 실패 : " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            //비밀번호 초기화 (보안상 목적)
            joinReqDto.deletePassword();

        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
