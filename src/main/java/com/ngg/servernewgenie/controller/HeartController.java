package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.dto.HeartRequestDTO;
import com.ngg.servernewgenie.dto.HeartResponseDTO;
import com.ngg.servernewgenie.service.HeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/heart")
public class HeartController {

    private final HeartService heartService;

    @Autowired
    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    @PostMapping("/heart/{storyId}")
    public ResponseEntity<HeartResponseDTO> addHeart(@RequestBody HeartRequestDTO requestDTO) {
        HeartResponseDTO heartDTO = heartService.addLike(requestDTO);
        return ResponseEntity.ok(heartDTO);
    }

    @DeleteMapping("/heart/delete/{userId}")
    public ResponseEntity<String> deleteHeart(@RequestBody HeartRequestDTO requestDTO) {
        heartService.deleteLike(requestDTO);
        return ResponseEntity.ok("좋아요 취소 성공");
    }

    @GetMapping("/heart/view/user/{userNum}")
    public ResponseEntity<List<HeartResponseDTO>> getLikedStoriesByUser(@PathVariable Long userNum) {
        List<HeartResponseDTO> likedStories = heartService.getLikedStoriesByUser(userNum);
        return ResponseEntity.ok(likedStories);
    }

    @GetMapping("/heart/view/story/{storyId}")
    public ResponseEntity<Integer> getLikeCountByStory(@PathVariable Long storyId) {
        int likeCount = heartService.getLikeCountByStory(storyId);
        return ResponseEntity.ok(likeCount);
    }
}
