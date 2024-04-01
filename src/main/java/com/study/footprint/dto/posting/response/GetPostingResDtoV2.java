package com.study.footprint.dto.posting.response;

import com.study.footprint.dto.comment.response.GetCommentResDto;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record GetPostingResDtoV2(

        Long postingId,
        Date recordDate,
        String title,
        String content,
        String imageUrl,
        String placeName,
        Long likes,
        String nickName,
        List<GetCommentResDto> commentList,
        Long commentNum,
        Boolean isLike

) {
}