package com.talktalkcare.domain.gameEvent.controller;

import com.talktalkcare.common.response.Api;
import com.talktalkcare.domain.gameEvent.dto.GameEventDto;
import com.talktalkcare.domain.gameEvent.service.GameEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games/event")
@RequiredArgsConstructor
public class GameEventController {

    private final GameEventService gameEventService;

    @PostMapping("")
    public Api<Void> processGameEvent(@RequestBody GameEventDto event) {
        gameEventService.processGameEvent(event);
        return Api.OK();
    }

}