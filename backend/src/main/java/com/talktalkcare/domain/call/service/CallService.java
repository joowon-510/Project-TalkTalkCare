package com.talktalkcare.domain.call.service;

import com.talktalkcare.domain.call.dto.CallDto;
import com.talktalkcare.domain.call.dto.CallInvitationDto;
import com.talktalkcare.domain.users.entity.User;
import com.talktalkcare.domain.users.error.UserErrorCode;
import com.talktalkcare.domain.users.exception.UserException;
import com.talktalkcare.domain.users.service.FriendService;
import com.talktalkcare.domain.users.service.UserService;
import com.talktalkcare.domain.users.service.UserStatusWebSocketHandler;
import lombok.RequiredArgsConstructor;
import okhttp3.Call;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallService {

    private final UserService userService;
    private final FriendService friendService;
    private final UserStatusWebSocketHandler userStatusWebSocketHandler;

    public void requestCall(CallDto callDto) {
        Integer callerId = callDto.getCallerId();
        User caller = userService.getUserById(callerId);

        String receiverPhone = callDto.getReceiverPhone();
        User receiver = userService.getUserIdByPhone(receiverPhone);

        String sessionid = callDto.getOpenviduSessionId();

        if(!friendService.isUserOnline(receiver.getUserId())) {
            throw new UserException(UserErrorCode.USER_OFFLINE);
        }

        CallInvitationDto invitation = new CallInvitationDto();
        invitation.setCallerId(callerId);
        invitation.setCallerName(caller.getName());
        invitation.setReceiverId(receiver.getUserId());
        invitation.setReceiverName(receiver.getName());
        invitation.setMessage("화상통화 요청이 도착했습니다.");
        invitation.setOpenviduSessionId(sessionid);

        userStatusWebSocketHandler.sendNotification(receiver.getUserId(), invitation);
    }

    public void acceptCall(CallDto callDto) {
        Integer callerId = callDto.getCallerId();
        Integer receiverId = callDto.getReceiverId();
        String openviduSessionId = callDto.getOpenviduSessionId();

        CallInvitationDto invitation = new CallInvitationDto();
        invitation.setCallerId(callerId);
        invitation.setReceiverId(receiverId);
        invitation.setMessage("요청을 수락하였습니다");
        invitation.setOpenviduSessionId(openviduSessionId);

        userStatusWebSocketHandler.sendNotification(callerId, invitation);
    }

    public void rejectCall(CallDto callDto) {
        Integer callerId = callDto.getCallerId();
        String receiverName = callDto.getReceiverName();
        String openviduSessionId = callDto.getOpenviduSessionId();

        CallInvitationDto invitation = new CallInvitationDto();
        invitation.setCallerId(callerId);
        invitation.setReceiverName(receiverName);
        invitation.setMessage("요청을 거절하였습니다");
        invitation.setOpenviduSessionId(openviduSessionId);

        userStatusWebSocketHandler.sendNotification(callerId, invitation);
    }

}
