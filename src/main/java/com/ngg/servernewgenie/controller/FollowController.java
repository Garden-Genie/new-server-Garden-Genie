package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.User;
import com.ngg.servernewgenie.dto.FollowSaveRequestDto;
import com.ngg.servernewgenie.repository.FollowRepository;
import com.ngg.servernewgenie.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowRepository followRepository;
    private final FollowService followService;

    @PostMapping("/follow/{toUserId}")
    public ResponseEntity followUser(FollowSaveRequestDto followSaveRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);
        }

        Long fromUserId = ((User) authentication.getPrincipal()).getUserNum();
        Long toUserId = followSaveRequestDto.getToUserId();
        followService.follow(fromUserId, toUserId);

        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);

    }

    @DeleteMapping("/unfollow/{fromUserId}")
    public ResponseEntity unfollowUser(FollowSaveRequestDto followSaveRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);
        }

        Long fromUserId = ((User) authentication.getPrincipal()).getUserNum();
        Long toUserId = followSaveRequestDto.getToUserId();
        followService.unfollow(fromUserId, toUserId);

        return new ResponseEntity("언팔로우 성공", HttpStatus.OK);
    }



}
