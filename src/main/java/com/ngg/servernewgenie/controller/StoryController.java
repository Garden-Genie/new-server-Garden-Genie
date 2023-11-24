package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ngg.servernewgenie.dto.StoryExplainResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/Story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    // POST 요청으로 Story 저장
    @PostMapping("/save")
    public ResponseEntity<String> saveStory(@RequestBody Story story) {
        storyService.save(story);
        return new ResponseEntity<>("Story saved successfully", HttpStatus.CREATED);
    }

//    // GET 요청으로 특정 사용자의 모든 Story 가져오기
//    @GetMapping("/view/{userId}")
//    public ResponseEntity<List<Story>> getUserStories(@PathVariable Long userId) {
//        List<Story> userStories = storyService.findByUserId(userId);
//        return new ResponseEntity<>(userStories, HttpStatus.OK);
//    }

    // GET 요청으로 특정 Story 가져오기
    @GetMapping("/view/{storyId}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long storyId) {
        Story story = storyService.findById(storyId);
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

//    // GET 요청으로 특정 사용자의 특정 이전 날짜 이후의 Story 가져오기
//    @GetMapping("/view/{fromUserId}")
//    public ResponseEntity<List<Story>> getStoriesFromUser(@PathVariable Long fromUserId,
//                                                          @RequestParam(name = "date") String date) {
//        try {
//            Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//            List<Story> userStories = storyService.findByUserIdAndDateGreaterThan(fromUserId, fromDate);
//            return new ResponseEntity<>(userStories, HttpStatus.OK);
//        } catch (ParseException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    // GET 요청으로 모든 Story 가져오기
    @GetMapping("/allStory")
    public ResponseEntity<List<Story>> getAllStories() {
        List<Story> allStories = storyService.findAll();
        return new ResponseEntity<>(allStories, HttpStatus.OK);
    }

    // DELETE 요청으로 특정 Story 삭제
    @GetMapping("/delete/{storyId}")
    public ResponseEntity<String> deleteStory(@PathVariable Long storyId) {
        storyService.delete(storyId);
        return new ResponseEntity<>("Story deleted successfully", HttpStatus.OK);
    }

    // uzin
    @PostMapping("/explain/save")
    public ResponseEntity<String> saveStoryExplain(@RequestHeader("storyId") Long storyId, @RequestBody String storyExplain) {
        storyService.saveStoryExplain(storyId, storyExplain); // body가 그대로 저장됨 (json으로 인식 못하는 듯)
        return ResponseEntity.ok("Explain saved successfully for storyId: " + storyId);
    }

    @GetMapping("/explain/view")
    public ResponseEntity<String> viewStoryExplain(@RequestHeader("storyId") Long storyId) {
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
