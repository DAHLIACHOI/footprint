package com.study.footprint.service.like;

import com.study.footprint.common.exception.CommonNotFoundException;
import com.study.footprint.common.response.SingleResult;
import com.study.footprint.domain.like.Like;
import com.study.footprint.domain.like.LikeRepository;
import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.member.MemberRepository;
import com.study.footprint.domain.posting.Posting;
import com.study.footprint.domain.posting.PostingRepository;
import com.study.footprint.dto.like.response.ClickLikeResDto;
import com.study.footprint.dto.like.response.CountLikeResDto;
import com.study.footprint.service.common.ResponseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostingRepository postingRepository;
    private final ResponseService responseService;


    public LikeService(LikeRepository likeRepository, MemberRepository memberRepository,
                       PostingRepository postingRepository, ResponseService responseService) {
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
        this.postingRepository = postingRepository;
        this.responseService = responseService;
    }


    @Transactional
    public SingleResult<ClickLikeResDto> clickLike(Long postingId, Long userId) {

        Member member = findMemberById(userId);
        Posting posting = findPostingById(postingId);

        boolean isLike = false;

        Optional<Like> like = likeRepository.findByMemberAndPosting(member, posting);

        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            likeRepository.save(Like.builder()
                    .member(member)
                    .posting(posting)
                    .build());
            isLike = true;
        }

        ClickLikeResDto clickLikeResDto = ClickLikeResDto.builder().isLike(isLike).build();

        return responseService.getSingleResult(clickLikeResDto);
    }

    private Member findMemberById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new CommonNotFoundException("userNotFound"));
    }

    private Posting findPostingById(Long postingId) {
        return postingRepository.findById(postingId)
                .orElseThrow(() -> new CommonNotFoundException("postingNotFound"));
    }


    public SingleResult<CountLikeResDto> countLike(Long postingId) {

        Posting posting = findPostingById(postingId);

        Long likeCount = likeRepository.countByPosting(posting);

        CountLikeResDto countLikeResDto = CountLikeResDto.builder().likeCount(likeCount).build();

        return responseService.getSingleResult(countLikeResDto);
    }


    @Transactional
    public SingleResult<ClickLikeResDto> clickLikeV2(Long postingId, Long userId) {
        return null;
    }



    public Boolean getIsLike(Posting posting, Member member) {

        return likeRepository.existsByPostingAndMember(posting, member);
    }


    public Long getLikeCount(Posting posting) {

        return likeRepository.countByPosting(posting);
    }
}
