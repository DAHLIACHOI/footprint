package com.study.footprint.domain.posting;

import com.study.footprint.domain.BaseEntity;
import com.study.footprint.domain.comment.Comment;
import com.study.footprint.domain.like.Like;
import com.study.footprint.domain.place.Place;
import com.study.footprint.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String imageUrl;

    private LocalDateTime recordDate;

    private Boolean isPublic; // 공개여부

    private Boolean isPosting; // 게시여부

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @PrePersist
    public void prePersist() {
        this.isPublic = this.isPublic == null ? true : isPublic;
        this.isPosting = this.isPosting == null ? true : isPosting;
    }

    @Builder
    public Posting(Long id, String content, String imageUrl, LocalDateTime recordDate, Boolean isPublic, Boolean isPosting, Place place, User user) {
        this.id = id;
        this.content = content;
        this.imageUrl = imageUrl;
        this.recordDate = recordDate;
        this.isPublic = isPublic;
        this.isPosting = isPosting;
        this.place = place;
        this.user = user;
    }
}
