package dev.codehouse.backend.admin.service;

import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRankingService {

    private final UserRepository userRepository;

    /**
     * User 들을 point 에 따라 내림차순 정렬하여 반환하는 함수
     * @return User List 형태로 반환
     */
    public List<User> getUserSortedByPoint(){
        Sort sort = Sort.by(Sort.Direction.DESC, "point");
        return userRepository.findAll(sort);
    }
}
