package com.study.footprint.domain.comment;

import com.study.footprint.domain.member.Member;
import com.study.footprint.domain.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT m.id as userId, " +
            "m.nickname as nickname, " +
            "c.content as content, " +
            "c.id as commentId " +
            "FROM Comment c join Member m " +
            "ON c.member = m " +
            "WHERE c.posting = :posting " +
            "ORDER BY c.createdDate DESC")
    List<GetCommentResDtoVo> findByPosting(Posting posting);

    Optional<Comment> findByIdAndMember(Long commentId, Member member);
}
