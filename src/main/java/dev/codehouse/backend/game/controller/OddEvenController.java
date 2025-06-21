package dev.codehouse.backend.game.controller;


import dev.codehouse.backend.game.dto.BetSummaryResponseDto;
import dev.codehouse.backend.game.dto.OddEvenRequestDto;
import dev.codehouse.backend.game.service.OddEvenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oddeven")
@RequiredArgsConstructor
public class OddEvenController {

    private final OddEvenService oddEvenService;

    @PostMapping("/bet")
    public void bet(@RequestBody OddEvenRequestDto dto) {
        oddEvenService.bet(dto);
    }

    @GetMapping("/roundSummary/{username}")
    public BetSummaryResponseDto getCurrentRoundSummary(@PathVariable String username) {
        return oddEvenService.getCurrentRoundResult(username);
    }


    @PostMapping("/calculate")
    public void calculate() {
        oddEvenService.calculateResult();
    }
}

