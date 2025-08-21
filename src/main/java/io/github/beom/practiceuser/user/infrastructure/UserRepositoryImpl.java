package io.github.beom.practiceuser.user.infrastructure;

import io.github.beom.practiceuser.user.domain.User;
import io.github.beom.practiceuser.user.application.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 리포지토리 구현체
 * Infrastructure 레이어에서 JPA를 통한 데이터 영속성을 담당
 */
@Repository
@RequiredArgsConstructor
@Log4j2
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<User> findById(String id) {
        log.info("사용자 ID로 조회: {}", id);
        
        return userJpaRepository.findById(id)
                .map(this::convertToUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("사용자 이메일로 조회: {}", email);
        
        return userJpaRepository.findByEmail(email)
                .map(this::convertToUser);
    }

    @Override
    public User save(User user) {
        log.info("사용자 저장: {}", user.getId());
        
        UserJpaEntity jpaEntity = convertToJpaEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(jpaEntity);
        
        return convertToUser(savedEntity);
    }

    @Override
    public boolean existsById(String id) {
        log.info("사용자 ID 존재 여부 확인: {}", id);
        
        return userJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        log.info("사용자 삭제: {}", id);
        
        userJpaRepository.deleteById(id);
    }

    /**
     * JPA 엔티티를 도메인 엔티티로 변환
     */
    private User convertToUser(UserJpaEntity jpaEntity) {
        return modelMapper.map(jpaEntity, User.class);
    }

    /**
     * 도메인 엔티티를 JPA 엔티티로 변환
     */
    private UserJpaEntity convertToJpaEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .pw(user.getPw())
                .email(user.getEmail())
                .del(user.isDel())
                .social(user.isSocial())
                .roleSet(user.getRoleSet())
                .build();
    }
}
