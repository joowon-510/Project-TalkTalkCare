package com.talktalkcare.domain.games.controller;

import com.talktalkcare.common.response.Api;
import com.talktalkcare.domain.games.dto.GameCategoryScoreDto;
import com.talktalkcare.domain.games.dto.GameRecordDto;
import com.talktalkcare.domain.games.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/save-result")
    public Api<Void> saveTestResult(@RequestBody GameRecordDto dto) {
        gameService.saveGameResult(dto);
        return Api.OK();
    }

    @GetMapping("monthly-stats/{userId}")
    public Api<List<GameCategoryScoreDto>> getMonthlyGameStats(@PathVariable Integer userId) {
        List<GameCategoryScoreDto> monthlyStats = gameService.getMonthlyGameStatsByCategory(userId);
        return Api.OK(monthlyStats);
    }
}
