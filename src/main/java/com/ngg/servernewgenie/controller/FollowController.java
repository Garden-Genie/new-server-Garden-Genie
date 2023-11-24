package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.CustomUserDetails;
import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.dto.FollowSaveRequestDto;
import com.ngg.servernewgenie.repository.FollowRepository;
import com.ngg.servernewgenie.repository.UserRepository;
import com.ngg.servernewgenie.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
        User toUser = userRepository.findById(userDetails.getUserNum())
                .orElseThrow(() -> new RuntimeException("인증된 사용자를 찾을 수 없습니다."));

        User fromUser = userRepository.findById(toUserId)
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
}
