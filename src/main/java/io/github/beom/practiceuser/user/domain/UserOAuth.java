package io.github.beom.practiceuser.user.domain;

import io.github.beom.practiceuser.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_oauth")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserOAuth extends CompleteBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String provider;  // "kakao", "google", "naver" 등

    @Column(nullable = false)
    private String providerId;  // 소셜 제공자의 고유 ID



    @Column(length = 1000)
    private String refreshToken;  // 소셜 제공자의 리프레시 토큰

    @Column
    private LocalDateTime tokenExpiry;  // 토큰 만료 시간



    // 소셜 제공자별 고유 식별자 생성
    public String getUniqueId() {
        return provider + "_" + providerId;
    }

    // 소셜 사용자 고유 식별자 (provider + providerId)
    public String getSocialUserId() {
        return provider + "_" + providerId;
    }

    // User의 email 가져오기
    public String getEmail() {
        return user != null ? user.getEmail() : null;
    }

    // 토큰 정보 업데이트
    public void updateTokens(String refreshToken, LocalDateTime tokenExpiry) {
        this.refreshToken = refreshToken;
        this.tokenExpiry = tokenExpiry;
    }

    // 토큰 만료 확인
    public boolean isTokenExpired() {
        return tokenExpiry != null && LocalDateTime.now().isAfter(tokenExpiry);
    }


}
