package com.talktalkcare.domain.gameEvent.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameEventDto {
    private String eventType;
    private String gameId;
    private Integer senderId;
    private Integer opponentUserId;
    private Map<String, Object> payload;
}
