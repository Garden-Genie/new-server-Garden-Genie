package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Follow;
import com.ngg.servernewgenie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Query(value = "INSERT INTO follow(from_user_id, to_user_id) VALUES(:fromUserId, :toUserId)", nativeQuery = true)
    void follow(@Param("fromUserId") Long fromUser, @Param("toUserId") Long toUser);


    @Modifying
    @Query(value = "DELETE FROM follow WHERE from_user_id = :fromUserId AND to_user_id = :toUserId", nativeQuery = true)
    void unfollow(@Param("fromUserId") Long fromUser, @Param("toUserId")Long toUser);

    List<Follow> findALlByfromUser(Long fromUser);

    List<Follow> findAllByToUser(Long toUser);

    List<Follow> findToUserIdByFromUser(Long fromUser);

    Optional<Follow> findFollowByFromUserAndToUser(Long fromUser, Long toUser);





}
