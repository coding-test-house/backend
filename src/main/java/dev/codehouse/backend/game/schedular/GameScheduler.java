package dev.codehouse.backend.game.schedular;

import dev.codehouse.backend.game.service.GameService;
import dev.codehouse.backend.game.service.OddEvenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameScheduler {

    private final OddEvenService oddEvenService;

    //각 게임 결과 로직 서비스 추가 예정
    @Scheduled(cron="0 50 * * * *")
    public void calculateResult() {
        oddEvenService.calculateResult();
    }
}
