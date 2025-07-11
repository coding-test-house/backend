package dev.codehouse.backend.game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.codehouse.backend.game.domain.Bet;
import dev.codehouse.backend.game.dto.BetSummaryResponseDto;
import dev.codehouse.backend.game.dto.MyBetDto;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.game.dto.OddEvenRequestDto;
import dev.codehouse.backend.game.repository.RedisRepository;
import dev.codehouse.backend.user.domain.UserHistory;
import dev.codehouse.backend.user.repository.UserRepository;
import dev.codehouse.backend.user.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OddEvenService implements GameService<OddEvenRequestDto> {

    private final RedisRepository redisRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
    private final UserHistoryService userHistoryService;

    // 라운드 키 - 시간 단위 (ex: 2025062017)
    private String getCurrentRoundKey() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }

    // 베팅 데이터 저장 키
    private String getBetsKey(String roundKey) {
        return "oddEven:" + roundKey + ":bets";
    }

    // 점수 저장 SortedSet 키
    private String getScoreKey(String roundKey, String betType) {
        return "oddEven:" + roundKey + ":score:" + betType.toLowerCase();
    }

    // 결과 저장 (홀짝 결과, 50분에 저장)
    private String getResultKey(String roundKey) {
        return "oddEven:" + roundKey + ":result";
    }

    // 베팅 가능 시간 00분~49분59초로 제한
    private void validateBettingTime() {
        int minute = LocalDateTime.now().getMinute();
        if (minute >= 50) {
            throw new IllegalStateException("베팅 가능 시간이 아닙니다. 00분부터 49분 59초까지만 베팅 가능합니다.");
        }
    }

    // 배팅 처리
//    @Override
//    public void bet(String username, OddEvenRequestDto dto) {
//        validateBettingTime();
//
//        String roundKey = getCurrentRoundKey();
//        String betsKey = getBetsKey(roundKey);
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + username));
//
//        if (user.getPoint() < dto.getBetAmount()) {
//            throw new IllegalArgumentException("포인트 부족");
//        }
//
//        Map<String, String> allBets = redisRepository.getAllBets(betsKey);
//
//        Bet updatedBet;
//
//        if (allBets != null && allBets.containsKey(username)) {
//            Bet existingBet;
//            try {
//                existingBet = objectMapper.readValue(allBets.get(username), Bet.class);
//            } catch (Exception e) {
//                throw new RuntimeException("Redis 데이터 파싱 실패", e);
//            }
//
//            if (!existingBet.getBetType().equalsIgnoreCase(dto.getBetType())) {
//                throw new IllegalArgumentException("'" + existingBet.getBetType() + "'에 이미 배팅한 유저는 반대 방향에 배팅할 수 없습니다.");
//            }
//
//            int newAmount = existingBet.getBetAmount() + dto.getBetAmount();
//            updatedBet = new Bet(username, newAmount, dto.getBetType());
//        } else {
//            updatedBet = new Bet(username, dto.getBetAmount(), dto.getBetType());
//        }
//
//        user.setPoint(user.getPoint() - dto.getBetAmount());
//        String betTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
//        user.getBetting().add(List.of(
//                betTimeStr,
//                dto.getBetType().equalsIgnoreCase("odd") ? "홀" : "짝",
//                String.valueOf(dto.getBetAmount())
//        ));
//        userRepository.save(user);
//
//        String betJson;
//        try {
//            betJson = objectMapper.writeValueAsString(updatedBet);
//        } catch (Exception e) {
//            throw new RuntimeException("베팅 데이터 직렬화 실패", e);
//        }
//
//        redisRepository.putBet(betsKey, username, betJson);
//
//        String scoreKey = getScoreKey(roundKey, dto.getBetType());
//        redisRepository.addScoreToSortedSet(scoreKey, username, dto.getBetAmount());
//    }

    @Override
    public void bet(String username, OddEvenRequestDto dto) {
        validateBettingTime();

        String roundKey = getCurrentRoundKey();
        String betsKey = getBetsKey(roundKey);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + username));

        if (user.getPoint() < dto.getBetAmount()) {
            throw new IllegalArgumentException("포인트 부족");
        }

        Map<String, String> allBets = redisRepository.getAllBets(betsKey);

        Bet updatedBet;

        if (allBets != null && allBets.containsKey(username)) {
            Bet existingBet;
            try {
                existingBet = objectMapper.readValue(allBets.get(username), Bet.class);
            } catch (Exception e) {
                throw new RuntimeException("Redis 데이터 파싱 실패", e);
            }

            if (!existingBet.getBetType().equalsIgnoreCase(dto.getBetType())) {
                throw new IllegalArgumentException("'" + existingBet.getBetType() + "'에 이미 배팅한 유저는 반대 방향에 배팅할 수 없습니다.");
            }

            int newAmount = existingBet.getBetAmount() + dto.getBetAmount();
            updatedBet = new Bet(username, newAmount, dto.getBetType());
        } else {
            updatedBet = new Bet(username, dto.getBetAmount(), dto.getBetType());
        }

        user.setPoint(user.getPoint() - dto.getBetAmount());

        String betTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        user.getBetting().add(List.of(
                betTimeStr,
                dto.getBetType().equalsIgnoreCase("odd") ? "홀" : "짝",
                String.valueOf(dto.getBetAmount())
        ));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시");
        String formattedDate = LocalDateTime.now().format(formatter);
        String betTypeKorean = dto.getBetType().equalsIgnoreCase("odd") ? "홀" : "짝";
        String reason = formattedDate + " 게임 " + betTypeKorean + " 배팅";
        user.getHistories().add(UserHistory.betting(user.getUsername(), reason, -dto.getBetAmount()));

        userRepository.save(user);

        // Redis 저장
        String betJson;
        try {
            betJson = objectMapper.writeValueAsString(updatedBet);
        } catch (Exception e) {
            throw new RuntimeException("베팅 데이터 직렬화 실패", e);
        }

        redisRepository.putBet(betsKey, username, betJson);

        String scoreKey = getScoreKey(roundKey, dto.getBetType());
        redisRepository.addScoreToSortedSet(scoreKey, username, dto.getBetAmount());
    }


    private String formatRoundKey(String roundKey) {
        // 예: "2025062415" → "2025년 06월 24일 15시"
        String year = roundKey.substring(0, 4);
        String month = roundKey.substring(4, 6);
        String day = roundKey.substring(6, 8);
        String hour = roundKey.substring(8, 10);
        return year + "년 " + month + "월 " + day + "일 " + hour + "시";
    }

    /**
     * 50분에 호출되는 결과 계산 메서드
     * 1) 현재 라운드(시 단위)의 베팅 데이터 조회
     * 2) 랜덤 홀짝 결과 생성 후 Redis에 저장 (resultKey)
     * 3) 맞춘 유저에게 포인트 차등 지급
     *
     */
    @Override
    public void calculateResult() {
        String roundKey = getCurrentRoundKey();
        String betsKey = getBetsKey(roundKey);

        Map<String, String> allBets = redisRepository.getAllBets(betsKey);
        if (allBets == null || allBets.isEmpty()) return;

        int resultNumber = random.nextInt(100) + 1;
        boolean isEven = (resultNumber % 2 == 0);
        String resultType = isEven ? "even" : "odd";

        redisRepository.putResult(getResultKey(roundKey), resultType);
        System.out.println("RESULT SAVED: key=" + getResultKey(roundKey) + ", value=" + resultType);

        Map<String, Bet> winBets = new HashMap<>();
        Map<String, Bet> loseBets = new HashMap<>();
        int totalWinAmount = 0;
        int totalLoseAmount = 0;

        for (Map.Entry<String, String> entry : allBets.entrySet()) {
            Bet bet;
            try {
                bet = objectMapper.readValue(entry.getValue(), Bet.class);
            } catch (Exception e) {
                throw new RuntimeException("Redis 데이터 파싱 실패", e);
            }

            boolean userChoseEven = bet.getBetType().equalsIgnoreCase("even");
            boolean win = (userChoseEven == isEven);

            if (win) {
                winBets.put(bet.getUsername(), bet);
                totalWinAmount += bet.getBetAmount();
            } else {
                loseBets.put(bet.getUsername(), bet);
                totalLoseAmount += bet.getBetAmount();
            }
        }

        // 포인트 분배
        for (Map.Entry<String, Bet> entry : winBets.entrySet()) {
            String username = entry.getKey();
            Bet bet = entry.getValue();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다: " + username));

            int originalBet = bet.getBetAmount();
            double ratio = (double) originalBet / totalWinAmount;
            int shareFromLosers = (int) (ratio * totalLoseAmount);

            int totalReturn = originalBet + shareFromLosers;

            user.setPoint(user.getPoint() + totalReturn);
            user.addGameResult(List.of(roundKey, "WIN", String.valueOf(originalBet), String.valueOf(shareFromLosers)));
            String formatted = formatRoundKey(roundKey);
            String reason = String.format("%s 게임 승리 보상 (+%dP)", formatted, shareFromLosers);
            user.getHistories().add(UserHistory.gameWin(username, reason, totalReturn));
            userRepository.save(user);
        }

        for (Map.Entry<String, Bet> entry : loseBets.entrySet()) {
            String username = entry.getKey();
            Bet bet = entry.getValue();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다: " + username));

            user.addGameResult(List.of(roundKey, "LOSE", String.valueOf(bet.getBetAmount())));
            String formatted = formatRoundKey(roundKey);
            String reason = String.format("%s 게임 패배 차감", formatted);
            user.getHistories().add(UserHistory.gameLoss(username, reason, 0));
            userRepository.save(user);
        }
    }


    /**
     * 00분에 호출되는 메서드 (새로운 라운드 시작 전 초기화)
     * 이전 라운드 결과 및 베팅 정보 삭제
     */
    public void resetForNewRound() {
        String previousRoundKey = getCurrentRoundKey();

        // 베팅, 점수, 결과 모두 삭제
        redisRepository.deleteBets(getBetsKey(previousRoundKey));
        redisRepository.deleteSortedSet(getScoreKey(previousRoundKey, "odd"));
        redisRepository.deleteSortedSet(getScoreKey(previousRoundKey, "even"));
        redisRepository.deleteResult(getResultKey(previousRoundKey));
    }

    /**
     * 50분~59분 사이에 결과를 조회할 때 호출
     * Redis에 저장된 결과를 가져와서 반환
     * 결과가 없으면 빈 데이터 반환
     */
    public BetSummaryResponseDto getCurrentRoundResult(String username) {
        String roundKey = getCurrentRoundKey();
        String betsKey = getBetsKey(roundKey);
        String resultKey = getResultKey(roundKey);

        int minute = LocalDateTime.now().getMinute();

        Map<String, String> allBets = redisRepository.getAllBets(betsKey);
        if (allBets == null) allBets = Collections.emptyMap();

        Map<String, Integer> oddBets = new HashMap<>();
        Map<String, Integer> evenBets = new HashMap<>();
        MyBetDto myBet = null;

        for (Map.Entry<String, String> entry : allBets.entrySet()) {
            try {
                Bet bet = objectMapper.readValue(entry.getValue(), Bet.class);
                if ("odd".equalsIgnoreCase(bet.getBetType())) {
                    oddBets.merge(bet.getUsername(), bet.getBetAmount(), Integer::sum);
                } else if ("even".equalsIgnoreCase(bet.getBetType())) {
                    evenBets.merge(bet.getUsername(), bet.getBetAmount(), Integer::sum);
                }

                if (bet.getUsername().equalsIgnoreCase(username.trim())) {
                    myBet = new MyBetDto(bet.getBetType(), bet.getBetAmount());
                }

            } catch (Exception e) {
                //
            }
        }

        BetSummaryResponseDto.SideSummary oddSummary = summarize(oddBets);
        BetSummaryResponseDto.SideSummary evenSummary = summarize(evenBets);

        // 50분 이후에는 결과 보여주기
        String resultType = null;
        if (minute >= 50) {
            resultType = redisRepository.getResult(resultKey);
            System.out.println("RESULT FETCHED: key=" + resultKey + ", value=" + resultType);
        }


        return new BetSummaryResponseDto(
                roundKey,
                evenSummary, // even이 먼저
                oddSummary,
                myBet,
                resultType // 50분 이전이면 null
        );
    }



    private BetSummaryResponseDto.SideSummary summarize(Map<String, Integer> userBets) {
        int total = userBets.values().stream().mapToInt(Integer::intValue).sum();

        List<BetSummaryResponseDto.TopBettor> top = userBets.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(e -> new BetSummaryResponseDto.TopBettor(e.getKey(), e.getValue()))
                .toList();

        return new BetSummaryResponseDto.SideSummary(total, top);
    }
}
