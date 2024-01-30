package com.study.footprint.service.member;

import com.study.footprint.domain.member.MemberRepository;
import com.study.footprint.dto.member.JoinReqDto;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Builder
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void join(JoinReqDto joinReqDto) {


    }
}
