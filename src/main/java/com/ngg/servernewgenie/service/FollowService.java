package com.ngg.servernewgenie.service;

import com.ngg.servernewgenie.domain.Follow;
import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.FollowRepository;
import com.ngg.servernewgenie.repository.StoryRepository;
import com.ngg.servernewgenie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowService {

    @Autowired
    private final FollowRepository followRepository;
    private final StoryRepository storyRepository;

    @Transactional
    public void follow(User fromUser, User toUser) {
        if (followRepository.findFollowByFromUserAndToUser(fromUser, toUser) != null) {
            throw new RuntimeException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow(fromUser, toUser);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(User fromUser, User toUser) {
        followRepository.unfollow(fromUser, toUser);
    }

    @Transactional
    public List<Follow> getFollowingList(User fromUser) {
        return followRepository.findAllByFromUser(fromUser);
    }

    public Long getFollowingCount(User fromUser) {
        return followRepository.countByFromUser(fromUser);
    }

//    @Transactional
//    public List<Story> followingStories(List<Follow> getFollowingList) {
//        List<Long> followingUserId = getFollowingList.stream()
//                .map(follow -> follow.getToUser().getUserNum())
//                .collect(Collectors.toList());
//
//        List<Story> followingStories = new ArrayList<>();
//
//        for (Long userId : followingUserId) {
//            List<Story> storiesForUser = storyRepository.findAllByUserId(userId);
//            followingStories.addAll(storiesForUser);
//        }
//
//        return followingStories;
//    }
}




