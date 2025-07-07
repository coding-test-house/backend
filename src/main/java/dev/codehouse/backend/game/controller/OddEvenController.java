package dev.codehouse.backend.game.controller;


import dev.codehouse.backend.game.dto.BetSummaryResponseDto;
import dev.codehouse.backend.game.dto.OddEvenRequestDto;
import dev.codehouse.backend.game.service.OddEvenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oddeven")
@RequiredArgsConstructor
public class OddEvenController {

    private final OddEvenService oddEvenService;

    @PostMapping("/bet")
    public void bet(Authentication authentication, @RequestBody OddEvenRequestDto dto) {
        oddEvenService.bet(authentication.getName(), dto);
    }

    @GetMapping("/roundSummary")
    public BetSummaryResponseDto getCurrentRoundSummary(Authentication authentication) {
        return oddEvenService.getCurrentRoundResult(authentication.getName());
    }

    @PostMapping("/calculate")
    public void calculate() {
        oddEvenService.calculateResult();
    }
}

