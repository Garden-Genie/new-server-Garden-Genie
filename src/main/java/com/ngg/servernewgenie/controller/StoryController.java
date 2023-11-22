package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.dto.StoryExplainResponseDto;
import com.ngg.servernewgenie.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Story")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @PostMapping("/explain/save")
    public ResponseEntity<String> saveStoryExplain(@RequestHeader("storyId") int storyId, @RequestBody String storyExplain) {
        storyService.saveStoryExplain(storyId, storyExplain); // body가 그대로 저장됨 (json으로 인식 못하는 듯)
        return ResponseEntity.ok("Explain saved successfully for storyId: " + storyId);
    }

    @GetMapping("/explain/view")
    public ResponseEntity<String> viewStoryExplain(@RequestHeader("storyId") int storyId) {
        String storyExplain = storyService.viewStoryExplain(storyId);
        if (storyExplain != null) {
            return ResponseEntity.ok(storyExplain);
            //return ResponseEntity.ok("Explain for storyId " + storyId + ": " + storyExplain);
            //StoryExplainResponseDto responseDto = new StoryExplainResponseDto(storyExplain);
            //return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Explain not found for storyId " + storyId);
        }
    }


//    @GetMapping("/explain/view")
//    public ResponseEntity<String> viewStoryExplain(@RequestParam("storyId") int storyId) {
//        String storyExplain = storyService.viewStoryExplain(storyId);
//        if (storyExplain != null) {
//            return ResponseEntity.ok("Explain for storyId " + storyId + ": " + storyExplain);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Explain not found for storyId " + storyId);
//        }
//    }

}
