package com.talktalkcare.domain.gameEvent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talktalkcare.common.error.ErrorCode;
import com.talktalkcare.common.error.ErrorCodeInterface;
import com.talktalkcare.common.exception.ApplicationException;
import com.talktalkcare.domain.gameEvent.dto.GameEventDto;
import com.talktalkcare.domain.users.service.UserStatusWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GameEventService {

    private final ObjectMapper objectMapper;

    public void processGameEvent(GameEventDto event) {
        WebSocketSession opponentSession = UserStatusWebSocketHandler.getUserSession(event.getOpponentUserId());
        if (opponentSession != null && opponentSession.isOpen()) {
            try {
                String jsonPayload = objectMapper.writeValueAsString(event);
                synchronized (opponentSession) {
                    opponentSession.sendMessage(new TextMessage(jsonPayload));
                }
            } catch (IOException e) {
               throw new ApplicationException(ErrorCode.SERVER_ERROR);
            }
        }
    }
}
