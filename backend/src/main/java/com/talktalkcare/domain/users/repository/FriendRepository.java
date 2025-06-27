package com.talktalkcare.domain.users.repository;

import com.talktalkcare.domain.users.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    @Query("SELECT f.friendId FROM Friend f WHERE f.userId = :userId")
    List<Integer> findFriendIdsByUserId(@Param("userId") Integer userId);

    @Query("SELECT f.userId FROM Friend f WHERE f.friendId = :friendId")
    List<Integer> findUserIdsByFriendId(@Param("friendId") Integer friendId);

    @Query("SELECT f FROM Friend f WHERE f.userId = :userId")
    List<Friend> findAllByUserId(@Param("userId") Integer userId);

    @Modifying
    void deleteByUserIdAndFriendId(Integer userId, Integer friendId);

    @Query("select f from Friend f where f.userId = :userId and f.friendId = :friendId")
    Friend findFriendByUserIdAndFriendId(Integer userId, Integer friendId);

}