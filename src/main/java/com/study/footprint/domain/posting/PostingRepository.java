package com.study.footprint.domain.posting;

import com.study.footprint.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    @Query("select p from Posting p left join fetch p.place where p.member = :member and p.isPosting = true")
    List<Posting> findAllByMemberFetch(Member member);

    @Query("select p from Posting p join fetch p.member left join fetch p.comments where p.id = :postingId and p.isPosting = true")
    Optional<Posting> findByIdFetch(Long postingId);
}
