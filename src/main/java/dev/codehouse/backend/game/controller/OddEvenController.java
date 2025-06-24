package dev.codehouse.backend.game.controller;


import dev.codehouse.backend.game.dto.BetSummaryResponseDto;
import dev.codehouse.backend.game.dto.OddEvenRequestDto;
import dev.codehouse.backend.game.service.OddEvenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oddeven")
@RequiredArgsConstructor
public class OddEvenController {

    private final OddEvenService oddEvenService;

    @PostMapping("/bet/{username}")
    public void bet(@PathVariable String username, @RequestBody OddEvenRequestDto dto) {
        oddEvenService.bet(username, dto);
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

