package io.github.beom.practiceuser.user.application;

import io.github.beom.practiceuser.user.domain.User;

import java.util.Optional;

/**
 * 사용자 도메인 리포지토리 인터페이스
 * DDD 패턴에 따라 도메인 레이어에 위치
 */
public interface UserRepository {
    
    /**
     * 사용자 ID로 사용자 조회
     * @param id 사용자 ID
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findById(String id);
    
    /**
     * 이메일로 사용자 조회
     * @param email 이메일
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 사용자 저장
     * @param user 저장할 사용자 정보
     * @return 저장된 사용자 정보
     */
    User save(User user);
    
    /**
     * 사용자 ID 존재 여부 확인
     * @param id 확인할 사용자 ID
     * @return 존재 여부
     */
    boolean existsById(String id);
    
    /**
     * 사용자 삭제
     * @param id 삭제할 사용자 ID
     */
    void deleteById(String id);
    
    /**
     * 이메일과 소셜 공급업체로 사용자 조회
     * @param email 이메일
     * @param socialProvider 소셜 공급업체
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByEmailAndSocialProvider(String email, String socialProvider);
}