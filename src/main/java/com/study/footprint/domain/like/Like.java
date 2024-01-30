package com.study.footprint.domain.like;

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
@Table(name = "likes")
@Entity
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Like(Long id, Posting posting, Member member) {
        this.id = id;
        this.posting = posting;
        this.member = member;
    }
}
