package com.study.footprint.domain.user;

import com.study.footprint.domain.BaseEntity;
import com.study.footprint.domain.comment.Comment;
import com.study.footprint.domain.like.Like;
import com.study.footprint.domain.posting.Posting;
import com.study.footprint.domain.report.Report;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String profileImageUrl;

    private Integer reportedCount;

    private LocalDateTime bannedDate;

    private Boolean certified;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posting> postings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.reportedCount = this.reportedCount == null ? 0 : this.reportedCount;
        this.certified = this.certified == null ? false : this.certified;
    }

    @Builder
    public User(Long id, String email, String nickname, String password, String profileImageUrl, Integer reportedCount, LocalDateTime bannedDate, Boolean certified, Set<Authority> authorities, List<Comment> comments, List<Posting> postings, List<Like> likes, List<Report> reports) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.reportedCount = reportedCount;
        this.bannedDate = bannedDate;
        this.certified = certified;
        this.authorities = authorities;
        this.comments = comments;
        this.postings = postings;
        this.likes = likes;
        this.reports = reports;
    }

}
