package com.study.footprint.domain.posting;

import com.study.footprint.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    @Query("select p from Posting p left join fetch p.place where p.member = :member")
    List<Posting> findAllByMemberFetch(Member member);
}
