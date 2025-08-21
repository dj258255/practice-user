package io.github.beom.practiceuser.user.application;

import io.github.beom.practiceuser.user.domain.User;
import io.github.beom.practiceuser.user.domain.UserRole;
import io.github.beom.practiceuser.user.presentation.UserService;
import io.github.beom.practiceuser.user.presentation.dto.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 애플리케이션 서비스 구현체
 * 비즈니스 로직을 처리하는 Application 레이어 서비스
 */
@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(UserRegisterDTO userRegisterDTO) throws IdExistException {
        log.info("사용자 회원가입 시작: {}", userRegisterDTO.getId());
        
        String id = userRegisterDTO.getId();

        // 아이디 중복 체크
        if (userRepository.existsById(id)) {
            throw new IdExistException("이미 존재하는 아이디입니다: " + id);
        }

        // 사용자 엔티티 생성 및 설정
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.changePw(passwordEncoder.encode(userRegisterDTO.getPw()));
        user.addRole(UserRole.USER);

        userRepository.save(user);
        log.info("사용자 회원가입 완료: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(String id) {
        log.info("사용자 조회: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + id));
    }

    @Override
    public void updateUser(String id, String email) {
        log.info("사용자 정보 수정: {} -> {}", id, email);
        
        User user = findById(id);
        user.changeEmail(email);
        userRepository.save(user);
        
        log.info("사용자 정보 수정 완료: {}", id);
    }

    @Override
    public void deleteUser(String id) {
        log.info("사용자 삭제: {}", id);
        
        User user = findById(id);
        user.changeDel(true); // 소프트 삭제
        userRepository.save(user);
        
        log.info("사용자 삭제 완료: {}", id);
    }
}
