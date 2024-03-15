package com.study.footprint.service.comment;


import com.study.footprint.common.exception.CommonNotFoundException;
import com.study.footprint.common.response.SingleResult;
import com.study.footprint.config.ConfigUtil;
import com.study.footprint.domain.comment.Comment;
import com.study.footprint.domain.comment.CommentRepository;
import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.member.MemberRepository;
import com.study.footprint.domain.posting.Posting;
import com.study.footprint.domain.posting.PostingRepository;
import com.study.footprint.dto.comment.request.UploadCommentReqDto;
import com.study.footprint.dto.comment.response.UploadCommentResDto;
import com.study.footprint.service.common.ResponseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    private final PostingRepository postingRepository;
    private final ConfigUtil configUtil;
    private final ResponseService responseService;


    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository,
                          PostingRepository postingRepository, ConfigUtil configUtil, ResponseService responseService) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.postingRepository = postingRepository;
        this.configUtil = configUtil;
        this.responseService = responseService;
    }

    private Member findLoginMember() {
        Long userId = configUtil.getLoginUserId();

        return memberRepository.findById(userId)
                .orElseThrow(() -> new CommonNotFoundException("userNotFound"));
    }

    private Posting findPosting(Long postingId) {
        return postingRepository.findById(postingId)
                .orElseThrow(() -> new CommonNotFoundException("postingNotFound"));
    }


    @Transactional
    public SingleResult<UploadCommentResDto> uploadCommentV1(Long postingId, UploadCommentReqDto uploadCommentReqDto) {
        Member member = findLoginMember();
        Posting posting = findPosting(postingId);

        Comment comment = Comment.builder()
                .content(uploadCommentReqDto.content())
                .member(member)
                .posting(posting)
                .build();

        commentRepository.save(comment);

        UploadCommentResDto uploadCommentResDto = UploadCommentResDto.builder()
                .commentId(comment.getId())
                .build();

        return responseService.getSingleResult(uploadCommentResDto);
    }
}
