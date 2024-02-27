package com.study.footprint.controller.member;

import com.study.footprint.dto.member.JoinReqDto;
import com.study.footprint.service.common.ResponseService;
import com.study.footprint.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    @Builder
    public MemberController(MemberService memberService, ResponseService responseService) {
        this.memberService = memberService;
        this.responseService = responseService;
    }


    @PostMapping("/v1/join")
    public ResponseEntity<String> join(@Valid @RequestBody JoinReqDto joinReqDto){

        try {
            memberService.join(joinReqDto);

        } catch (Exception e) {
            log.error("회원 가입 실패 : " + e);

        }

        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }
}
