package io.github.beom.practiceuser.user.presentation;

import io.github.beom.practiceuser.user.domain.User;
import io.github.beom.practiceuser.user.presentation.dto.UserRegisterDTO;

/**
 * 사용자 애플리케이션 서비스 인터페이스
 * DDD 패턴에 따라 Application 레이어에 위치
 */
public interface UserService {
    
    /**
     * 사용자 회원가입 예외 클래스
     */
    class IdExistException extends Exception {
        public IdExistException(String message) {
            super(message);
        }
    }
    
    /**
     * 사용자 회원가입
     * @param userRegisterDTO 회원가입 정보
     * @throws IdExistException 아이디가 이미 존재할 경우
     */
    void join(UserRegisterDTO userRegisterDTO) throws IdExistException;
    
    /**
     * 사용자 ID로 조회
     * @param id 사용자 ID
     * @return 사용자 정보
     */
    User findById(String id);
    
    /**
     * 사용자 정보 수정
     * @param id 사용자 ID
     * @param email 새 이메일
     */
    void updateUser(String id, String email);
    
    /**
     * 사용자 삭제
     * @param id 사용자 ID
     */
    void deleteUser(String id);
}