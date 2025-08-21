package io.github.beom.practiceuser.user.application;

import io.github.beom.practiceuser.user.domain.UserOAuth;

import java.util.Optional;

/**
 * 소셜 로그인 사용자 리포지토리 인터페이스
 */
public interface UserOAuthRepository {
    
    /**
     * 이메일과 공급업체로 소셜 사용자 조회
     * @param email 이메일
     * @param provider 공급업체
     * @return 소셜 사용자 정보 (Optional)
     */
    Optional<UserOAuth> findByEmailAndProvider(String email, String provider);
    
    /**
     * 공급업체 ID로 소셜 사용자 조회
     * @param providerId 공급업체의 고유 ID
     * @param provider 공급업체
     * @return 소셜 사용자 정보 (Optional)
     */
    Optional<UserOAuth> findByProviderIdAndProvider(String providerId, String provider);
    
    /**
     * 소셜 사용자 저장
     * @param userOAuth 저장할 소셜 사용자 정보
     * @return 저장된 소셜 사용자 정보
     */
    UserOAuth save(UserOAuth userOAuth);
    
    /**
     * 이메일로 소셜 사용자 존재 여부 확인
     * @param email 이메일
     * @param provider 공급업체
     * @return 존재 여부
     */
    boolean existsByEmailAndProvider(String email, String provider);
}
