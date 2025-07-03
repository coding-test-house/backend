
package dev.codehouse.backend.user.service;

import dev.codehouse.backend.global.exception.AuthException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.dto.UserRequest;
import dev.codehouse.backend.user.repository.UserRepository;
import dev.codehouse.backend.global.util.JwtUtil;
import dev.codehouse.backend.user.service.external.SolvedAcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 사용자 인증, 등록 및 사용자 관리 기능을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final SolvedAcClient solvedAcClient;

    /**
     * solved.ac API를 통해 사용자 존재 여부를 확인
     *
     * @param username 확인할 사용자 이름
     * @throws RuntimeException API 호출 실패시
     */
    public void userExists(String username, String className) {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthException(ResponseCode.USER_NOT_FOUND);
        }
        if(!solvedAcClient.verifyUserClass(username, className)){
            throw new AuthException(ResponseCode.USER_NOT_FOUND);
        }
    }

    private void validateRequest(UserRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            throw new AuthException(ResponseCode.INVALID_REQUEST);
        }
    }

    /**
     * solved.ac 정보 검증 후 새로운 사용자 등록
     *
     * @param request 등록 정보가 담긴 DTO
     * @throws IllegalArgumentException 이미 존재하는 사용자이거나 클래스 정보가 유효하지 않은 경우
     * @throws RuntimeException         API 호출 실패시
     */
    public void register(UserRequest request) {
        validateRequest(request);

        if(userRepository.existsByUsername(request.getUsername())){
            throw new DuplicateKeyException("이미 존재하는 사용자입니다.");
        }
        User user = User.of(request.getUsername(), passwordEncoder.encode(request.getPassword()),request.getClasses());
        userRepository.save(user);
    }


    /**
     * 사용자 인증 및 JWT 토큰 생성
     *
     * @param request 로그인 정보가 담긴 DTO
     * @return 액세스 토큰과 리프레시 토큰이 담긴 Map
     * @throws IllegalArgumentException 사용자를 찾을 수 없거나 비밀번호가 일치하지 않는 경우
     */
    public Map<String, String> login(UserRequest request) {
        validateRequest(request);
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthException(ResponseCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException(ResponseCode.INVALID_PASSWORD);
        }

        return Map.of(
          "accessToken", jwtUtil.generateAccessToken(user.getUsername()),
                "refreshToken", jwtUtil.generateRefreshToken(user.getUsername())
        );
    }

}