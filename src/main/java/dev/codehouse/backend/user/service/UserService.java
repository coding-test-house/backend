package dev.codehouse.backend.user.service;

import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.dto.UserRequestDto;
import dev.codehouse.backend.user.repository.UserRepository;
import dev.codehouse.backend.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 사용자 인증, 등록 및 사용자 관리 기능을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * solved.ac API를 통해 사용자 존재 여부를 확인
     *
     * @param username 확인할 사용자 이름
     * @throws RuntimeException API 호출 실패시
     */
    public void userExists(String username) {
        try {
            fetchUserInfo(username);
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify user existence: " + username, e);
        }
    }

    /**
     * 데이터베이스의 모든 사용자 조회
     *
     * @return 모든 사용자 목록
     */
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * solved.ac API에서 사용자 정보를 가져옴
     *
     * @param handle 사용자의 핸들/아이디
     * @return 사용자 정보가 담긴 JSONObject
     * @throws Exception API 호출 실패 또는 사용자를 찾을 수 없는 경우
     */
    private JSONObject fetchUserInfo(String handle) throws Exception {
        String urlStr = "https://solved.ac/api/v3/user/show?handle=" + handle;
        URL url = new URL(urlStr);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "JavaRatingFetcher");

            int responseCode = conn.getResponseCode();
            if (responseCode == 404) {
                throw new IllegalArgumentException("해당 아이디를 찾을 수 없습니다.");
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                return new JSONObject(responseBuilder.toString());
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * solved.ac 정보 검증 후 새로운 사용자 등록
     *
     * @param request 등록 정보가 담긴 DTO
     * @throws IllegalArgumentException 이미 존재하는 사용자이거나 클래스 정보가 유효하지 않은 경우
     * @throws RuntimeException         API 호출 실패시
     */
    public void register(UserRequestDto request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다");
        }


        JSONObject json;
        try {
            json = fetchUserInfo(request.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int rating = json.getInt("rating");
        String className = json.getString("bio");
        if(className.compareTo(request.getClasses()) != 0) {
            throw new IllegalArgumentException("올바르지 않은 회차");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .point(rating)
                .classes(request.getClasses())
                .startPoint(rating)
                .build();

        userRepository.save(user);
    }


    /**
     * 사용자 인증 및 JWT 토큰 생성
     *
     * @param request 로그인 정보가 담긴 DTO
     * @return 액세스 토큰과 리프레시 토큰이 담긴 Map
     * @throws IllegalArgumentException 사용자를 찾을 수 없거나 비밀번호가 일치하지 않는 경우
     */
    public Map<String, String> login(UserRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("password not match");
        }
        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    /**
     * 사용자 이름으로 사용자 정보 조회
     *
     * @param username 조회할 사용자 이름
     * @return 사용자 엔티티
     * @throws IllegalArgumentException 사용자를 찾을 수 없는 경우
     */
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("not found"));
    }

    /**
     * 현재 solved.ac 레이팅을 기준으로 모든 사용자의 포인트 업데이트
     *
     * @throws RuntimeException API 호출 실패시
     */
    public void updateAllUsers() {
        while (userRepository.count() > 0) {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                try {
                    updateUserPoints(user);
                    Thread.sleep(60000); // 60초 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("User update interrupted", e);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to update user: " + user.getUsername(), e);
                }
            }
        }
    }

    private void updateUserPoints(User user) throws Exception {
        JSONObject json = fetchUserInfo(user.getUsername());
        int increasedRating = json.getInt("rating") - user.getStartPoint();
        user.setPoint(user.getPoint() + increasedRating);
        userRepository.save(user);
    }

    /**
     * 특정 사용자의 정보 업데이트
     *
     * @param username 업데이트할 사용자의 이름
     * @throws IllegalArgumentException 사용자를 찾을 수 없는 경우
     */
    public void updateUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("not found"));
        userRepository.save(user);
    }
}
