package com.talktalkcare.domain.users.service;

import com.talktalkcare.domain.users.dto.AddFriendReq;
import com.talktalkcare.domain.users.dto.DeleteFriendReq;
import com.talktalkcare.domain.users.dto.FriendDto;
import com.talktalkcare.domain.users.entity.Friend;
import com.talktalkcare.domain.users.entity.User;
import com.talktalkcare.domain.users.error.UserErrorCode;
import com.talktalkcare.domain.users.exception.UserException;
import com.talktalkcare.domain.users.repository.FriendRepository;
import com.talktalkcare.domain.users.repository.UserRepository;
import com.talktalkcare.infrastructure.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

    private final RedisRepository redisRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private static final Logger log = LoggerFactory.getLogger(FriendService.class);

    private static final String USER_STATUS_PREFIX = "user:status:";
    private static final String USER_LAST_ACTIVE_PREFIX = "user:lastActive:";

    public void setUserOnline(Integer userId) {
        log.info("사용자 {} 온라인 상태로 설정", userId);
        redisRepository.save(USER_STATUS_PREFIX + userId, "ONLINE");
        updateLastActiveTime(userId);
    }

    public void setUserOffline(Integer userId) {
        log.info("사용자 {} 오프라인 상태로 설정", userId);
        redisRepository.save(USER_STATUS_PREFIX + userId, "OFFLINE");
        updateLastActiveTime(userId);
    }

    @Transactional(readOnly = true)
    public boolean isUserOnline(Integer userId) {
        // Map에서 세션 존재 여부로 온라인 상태 확인
        return UserStatusWebSocketHandler.isUserConnected(userId);
    }

    public void updateLastActiveTime(Integer userId) {
        redisRepository.save(
                USER_LAST_ACTIVE_PREFIX + userId,
                LocalDateTime.now().toString(),
                24
        );
    }

    @Transactional(readOnly = true)
    public LocalDateTime getLastActiveTime(Integer userId) {
        String time = (String) redisRepository.find(USER_LAST_ACTIVE_PREFIX + userId);
        return time != null ? LocalDateTime.parse(time) : null;
    }

    @Transactional(readOnly = true)
    public List<FriendDto> getFriendsStatus(Integer userId) {
        log.info("getFriendsStatus 호출 - userId: {}", userId);
        
        List<FriendDto> friendsStatus = new ArrayList<>();
        List<Friend> friends = friendRepository.findAllByUserId(userId);
        
        log.info("조회된 친구 목록: {}", friends);

        for (Friend friend : friends) {
            userRepository.findById(friend.getFriendId()).ifPresent(friendUser -> {
                boolean isOnline = isUserOnline(friend.getFriendId());
                LocalDateTime lastActiveTime = getLastActiveTime(friend.getFriendId());

                FriendDto friendDto = FriendDto.from(
                        friend.getFriendId(),
                        friend.getFriendName(),
                        friendUser.getS3FileName(),
                        friendUser.getPhone(),
                        isOnline,
                        lastActiveTime
                );

                friendsStatus.add(friendDto);
                log.info("친구 정보 추가: {}", friendDto);
            });
        }

        log.info("최종 반환되는 친구 목록: {}", friendsStatus);
        return friendsStatus;
    }

    @Transactional(readOnly = true)
    public FriendDto getFriendStatus(Integer friendId) {
        return userRepository.findById(friendId)
                .map(friend -> FriendDto.from(
                        friendId,
                        friend.getName(),
                        friend.getS3FileName(),
                        friend.getPhone(),
                        isUserOnline(friendId),
                        getLastActiveTime(friendId)
                ))
                .orElse(null);
    }

    // 모든 사용자의 상태 정보 초기화 (서버 재시작시 사용)
    public void resetAllUserStatus() {
        String pattern = USER_STATUS_PREFIX + "*";
        for(String key : redisRepository.keys(pattern)) {
            redisRepository.delete(key);
        }
    }

    @Transactional(readOnly = true)
    public List<Integer> getFriendIds(Integer userId) {
        return friendRepository.findFriendIdsByUserId(userId);
    }

    public void addFriend(AddFriendReq addFriendReq) {
        User friend = userRepository.findByPhone(addFriendReq.getPhone())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Friend relationship = friendRepository.findFriendByUserIdAndFriendId(addFriendReq.getUserId(), friend.getUserId());

        if(relationship == null) {
            friendRepository.save(new Friend(
                    addFriendReq.getUserId(),
                    friend.getUserId(),
                    addFriendReq.getName()));

            return;
        }

        throw new UserException(UserErrorCode.FRIEND_ALREADY_EXIST);
    }

    public void removeFriend(DeleteFriendReq deleteFriendReq) {
        friendRepository.deleteByUserIdAndFriendId(deleteFriendReq.getUserId(), deleteFriendReq.getFriendId());
    }
}