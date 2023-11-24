package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Follow;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.FollowRepository;
import com.ngg.servernewgenie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {

    @Autowired
    private final FollowRepository followRepository;

    @Transactional
    public void follow(User fromUser, User toUser) {
        if (followRepository.findFollowByFromUserAndToUser(fromUser, toUser) != null) {
            throw new RuntimeException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow(fromUser, toUser);
        followRepository.save(follow);
    }
}





