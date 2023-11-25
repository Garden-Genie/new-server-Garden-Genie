package com.ngg.servernewgenie.repository;

import com.ngg.servernewgenie.domain.Follow;
import com.ngg.servernewgenie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Query("DELETE FROM Follow WHERE fromUser = :fromUser AND toUser = :toUser")
    void unfollow(@Param("fromUser") User fromUser, @Param("toUser") User toUser);

    List<Follow> findAllByFromUser(User fromUser);

    Long countByFromUser(User fromUser);

    List<Follow> findAllByToUser(User toUser);

    List<Follow> findToUserByFromUser(User fromUser);

    Follow findFollowByFromUserAndToUser(User fromUser, User toUser);

}


