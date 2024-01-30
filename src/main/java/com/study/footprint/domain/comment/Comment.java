package com.study.footprint.domain.comment;

import com.study.footprint.domain.BaseEntity;
import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.posting.Posting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @Builder
    public Comment(Long id, String content, Member member, Posting posting) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.posting = posting;
    }

}
