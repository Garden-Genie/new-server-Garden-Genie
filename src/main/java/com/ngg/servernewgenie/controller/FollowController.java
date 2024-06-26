package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.CustomUserDetails;
import com.ngg.servernewgenie.domain.Follow;
import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.repository.FollowRepository;
import com.ngg.servernewgenie.repository.UserRepository;
import com.ngg.servernewgenie.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    @PostMapping("/follow/{toUserId}")
    public ResponseEntity followUser(@PathVariable Long toUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User fromUser = userRepository.findById(userDetails.getUserNum())
                .orElseThrow(() -> new RuntimeException("인증된 사용자를 찾을 수 없습니다."));

        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("팔로우 대상 사용자를 찾을 수 없습니다."));

        followService.follow(fromUser, toUser);

        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }


    @DeleteMapping("/follow/{toUserId}")
    public ResponseEntity unfollowUser(@PathVariable Long toUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User fromUser = userRepository.findById(userDetails.getUserNum()).orElse(null);
        User toUser = userRepository.findById(toUserId).orElse(null);

        if (fromUser == null || toUser == null) {
            return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        followService.unfollow(fromUser, toUser);

        return new ResponseEntity<>("언팔로우 성공", HttpStatus.OK);
    }

    @GetMapping("/follow/following")
    public ResponseEntity<List<User>> getFollowingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<List<User>>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User fromUser = userRepository.findById(userDetails.getUserNum()).orElse(null);

        if (fromUser == null) {
            return new ResponseEntity<List<User>>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

        List<Follow> followingList = followService.getFollowingList(fromUser);
        List<User> followingUsers = followingList.stream().map(Follow::getToUser).collect(Collectors.toList());

        return new ResponseEntity<>(followingUsers, HttpStatus.OK);
    }

    @GetMapping("/follow/followingNums")
    public ResponseEntity<List<Long>> getFollowingUserNums() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            User fromUser = userRepository.findById(userDetails.getUserNum()).orElse(null);

            if (fromUser == null) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }

            List<Long> followingUserIds = followService.getFollowingUserNums(fromUser);

            return ResponseEntity.ok(followingUserIds);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/follow/followingUserIds")
    public ResponseEntity<List<String>> getFollowingUserIds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            User fromUser = userRepository.findById(userDetails.getUserNum()).orElse(null);

            if (fromUser == null) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }

            List<String> followingUserIds = followService.getFollowingUserIds(fromUser);

            return ResponseEntity.ok(followingUserIds);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/follow/followingCount/{userId}")
    public ResponseEntity<Long> getFollowingCount(@PathVariable Long userId) {
        User user = new User();
        user.setUserNum(userId);

        Long followingCount = followService.getFollowingCount(user);
        return new ResponseEntity<>(followingCount, HttpStatus.OK);
    }


}
