package com.talktalkcare.domain.users.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userId;

    private Integer friendId;

    private String friendName;

    public Friend(Integer userId, Integer friendId, String friendName) {
        this.userId = userId;
        this.friendId = friendId;
        this.friendName = friendName;
    }
}