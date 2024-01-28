package com.study.footprint.domain.user;

import com.study.footprint.common.converter.StringCryptoUniqueConverter;
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
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 496)
    @Convert(converter = StringCryptoUniqueConverter.class)
    private String email;

    @Column(nullable = false, length = 200)
    @Convert(converter = StringCryptoUniqueConverter.class)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String password;

    private String profileImageUrl;

    private Integer reportedCount;

    private LocalDateTime bannedDate;

    private Boolean certified;

    private Boolean isDeleted; //탈퇴여부

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
        this.isDeleted = this.isDeleted == null ? false : this.isDeleted;
    }

    @Builder
    public User(Long id, String email, String nickname, String password, String profileImageUrl,
                Integer reportedCount, LocalDateTime bannedDate, Boolean certified, Boolean isDeleted,
                List<Comment> comments, List<Posting> postings,
                List<Like> likes, List<Report> reports) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.reportedCount = reportedCount;
        this.bannedDate = bannedDate;
        this.certified = certified;
        this.isDeleted = isDeleted;
        this.comments = comments;
        this.postings = postings;
        this.likes = likes;
        this.reports = reports;
    }

}
