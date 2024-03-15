package com.study.footprint.dto.posting.response;

import com.study.footprint.domain.comment.GetCommentResDtoVo;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record GetPostingResDto(

        Long postingId,
        Date recordDate,
        String title,
        String content,
        String imageUrl,
        String placeName,
        Long likes,
        String nickName,
        List<GetCommentResDtoVo> commentList,
        Long commentNum,
        Boolean isLike

) {
}
