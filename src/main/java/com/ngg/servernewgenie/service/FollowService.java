package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        if(followRepository.findFollowByFromUserAndToUser(fromUserId, toUserId) != null)
            throw new RuntimeException("이미 팔로우한 사용자입니다.");
        followRepository.follow(fromUserId, toUserId);
    }

    @Transactional
    public void unfollow(Long fromUserId, Long toUserId) {
        followRepository.unfollow(fromUserId, toUserId);
    }

}
