package com.study.footprint.domain.like;

import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPosting(Member member, Posting posting);

    Long countByPosting(Posting posting);

    Boolean existsByPostingAndMember(Posting posting, Member member);
}
