package com.talktalkcare.domain.games.service;

import com.talktalkcare.domain.games.dto.GameCategoryScoreDto;
import com.talktalkcare.domain.games.dto.GameRecordDto;
import com.talktalkcare.domain.games.entity.GameCategoryAvgScore;
import com.talktalkcare.domain.games.entity.GameScorePerDay;
import com.talktalkcare.domain.games.repository.GameCategoryAvgScoreRepository;
import com.talktalkcare.domain.games.repository.GameRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRecordRepository gameRecordRepository;
    private final GameCategoryAvgScoreRepository gameCategoryAvgScoreRepository;

    @Transactional
    public GameScorePerDay saveGameResult(GameRecordDto dto) {
        GameScorePerDay result = new GameScorePerDay();
        result.setUserId(dto.getUserId());
        result.setGameId(dto.getGameId());
        result.setScore((short)dto.getScore());
        result.setPlayedAt(LocalDateTime.now());
        
        return gameRecordRepository.save(result);
    }

    @Transactional(readOnly = true)
    public List<GameCategoryScoreDto> getMonthlyGameStatsByCategory(Integer userId) {
        List<GameCategoryScoreDto> result = new ArrayList<>();

        for(int i=1; i<6; i++) {
            GameCategoryScoreDto gameCategoryScoreDto = new GameCategoryScoreDto();
            gameCategoryScoreDto.setUserId(userId);
            gameCategoryScoreDto.setCategoryId(i);
            
            Optional<GameCategoryAvgScore> categoryScore = gameCategoryAvgScoreRepository.findByUserIdAndCategoryId(userId, i);
            
            if(categoryScore.isPresent()) {
                gameCategoryScoreDto.setPlayedCount(categoryScore.get().getPlayedCount());
                gameCategoryScoreDto.setAverage(categoryScore.get().getAverage());
            } else {
                gameCategoryScoreDto.setPlayedCount((short)0);
                gameCategoryScoreDto.setAverage(0.0f);
            }
            
            result.add(gameCategoryScoreDto);
        }
        
        return result;
    }
}
