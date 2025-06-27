package com.talktalkcare.domain.call.controller;

import com.talktalkcare.common.response.Api;
import com.talktalkcare.domain.call.dto.CallDto;
import com.talktalkcare.domain.call.dto.CallInvitationDto;
import com.talktalkcare.domain.call.service.CallService;
import com.talktalkcare.domain.users.dto.FriendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/call")
public class CallController {

    private final CallService callService;

    @PostMapping("/request")
    public Api<Void> requestCall(@RequestBody CallDto callDto) {
        callService.requestCall(callDto);

        return Api.OK();
    }

    @PostMapping("/accept")
    public Api<Void> acceptCall(@RequestBody CallDto callDto) {
        callService.acceptCall(callDto);

        return Api.OK();
    }

    @PostMapping("/reject")
    public Api<Void> rejectCall(@RequestBody CallDto callDto) {
        callService.rejectCall(callDto);

        return Api.OK();
    }

}
