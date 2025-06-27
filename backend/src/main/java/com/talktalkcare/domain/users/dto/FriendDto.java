package com.talktalkcare.domain.users.dto;

import com.talktalkcare.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@ToString
public class FriendDto {
    private final Integer userId;
    private final String name;
    private final String s3Filename;
    private final String phone;
    private String status;
    private LocalDateTime lastActiveTime;
    private String displayStatus;

    @Builder
    public FriendDto(Integer userId, String name, String s3Filename, String phone, 
                    String status, LocalDateTime lastActiveTime, String displayStatus) {
        this.userId = userId;
        this.name = name;
        this.s3Filename = s3Filename;
        this.phone = phone;
        this.status = status;
        this.lastActiveTime = lastActiveTime;
        this.displayStatus = displayStatus;
    }

    public static FriendDto from(Integer userId, String name, String s3Filename, 
                               String phone, boolean isOnline, LocalDateTime lastActiveTime) {
        String status = isOnline ? "ONLINE" : "OFFLINE";
        String displayStatus = isOnline ? "온라인" : "오프라인";
        return FriendDto.builder()
                .userId(userId)
                .name(name)
                .s3Filename(s3Filename)
                .phone(phone)
                .status(status)
                .lastActiveTime(lastActiveTime)
                .displayStatus(displayStatus)
                .build();
    }

    public static FriendDto fromUser(User user, boolean isOnline, LocalDateTime lastActiveTime) {
        String status = isOnline ? "ONLINE" : "OFFLINE";
        String displayStatus = isOnline ? "온라인" : "오프라인";
        return FriendDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .s3Filename(user.getS3FileName())
                .phone(user.getPhone())
                .status(status)
                .lastActiveTime(lastActiveTime)
                .displayStatus(displayStatus)
                .build();
    }

    private static String createDisplayStatus(LocalDateTime lastActiveTime, boolean isOnline) {
        if(isOnline) {
            return "온라인";
        }

        if (lastActiveTime == null) {
            return "오프라인";
        }

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(lastActiveTime, now);

        if (duration.isNegative()) {
            return "온라인";
        }

        long minutes = duration.toMinutes();
        if (minutes < 1) {
            return "방금 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return hours + "시간 전";
        }

        return duration.toDays() + "일 전";
    }

    public void updateStatus(boolean isOnline, LocalDateTime lastActiveTime) {
        this.status = isOnline ? "ONLINE" : "OFFLINE";
        this.lastActiveTime = lastActiveTime;
        this.displayStatus = isOnline ? "온라인" : "오프라인";
    }

//    public void updateProfileImage(String s3Filename) {
//        this.s3Filename = s3Filename;
//    }
}