package com.study.footprint.domain.comment;

import com.study.footprint.domain.BaseEntity;
import com.study.footprint.domain.posting.Posting;
import com.study.footprint.domain.user.User;
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
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @Builder
    public Comment(String content, User user, Posting posting) {
        this.content = content;
        this.user = user;
        this.posting = posting;
    }

}
