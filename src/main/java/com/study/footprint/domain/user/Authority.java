package com.study.footprint.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    private String role;

    public static Authority ofUser(User user) {
        return Authority.builder()
                .role("ROLE_USER")
                .user(user)
                .build();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
