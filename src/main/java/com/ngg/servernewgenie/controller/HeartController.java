package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.dto.HeartDTO;
import com.ngg.servernewgenie.service.HeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/heart")
public class HeartController {

    private final HeartService heartService;

    @Autowired
    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    @PostMapping("/heart/{storyId}")
    public ResponseEntity<HeartDTO> addHeart(@PathVariable Long userId, @PathVariable Long storyId) {
        HeartDTO heartDTO = heartService.addLike(userId, storyId);
        return ResponseEntity.ok(heartDTO);
    }

    @DeleteMapping("/heart/delete/{userId}")
    public ResponseEntity<String> deleteHeart(@PathVariable Long userId, @PathVariable Long storyId) {
        heartService.deleteLike(userId, storyId);
        return ResponseEntity.ok("좋아요 취소 성공");
    }

    @GetMapping("/heart/view/{userId}")
    public ResponseEntity<List<HeartDTO>> getLikedStoriesByUser(@PathVariable Long userId) {
        List<HeartDTO> likedStories = heartService.getLikedStoriesByUser(userId);
        return ResponseEntity.ok(likedStories);
    }

    @GetMapping("/heart/view/{storyId}")
    public ResponseEntity<Integer> getLikeCountByStory(@PathVariable Long storyId) {
        int likeCount = heartService.getLikeCountByStory(storyId);
        return ResponseEntity.ok(likeCount);
    }
}
