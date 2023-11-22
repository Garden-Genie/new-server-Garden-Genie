package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    // POST 요청으로 Story 저장
    @PostMapping("/save")
    public ResponseEntity<String> saveStory(@RequestBody Story story) {
        storyService.save(story);
        return new ResponseEntity<>("Story saved successfully", HttpStatus.CREATED);
    }

    // GET 요청으로 특정 사용자의 모든 Story 가져오기
    @GetMapping("/view/{userId}")
    public ResponseEntity<List<Story>> getUserStories(@PathVariable Long userId) {
        List<Story> userStories = storyService.findByUserId(userId);
        return new ResponseEntity<>(userStories, HttpStatus.OK);
    }

    // GET 요청으로 특정 Story 가져오기
    @GetMapping("/view/{storyId}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long storyId) {
        Story story = storyService.findById(storyId);
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

    // GET 요청으로 특정 사용자의 특정 이전 날짜 이후의 Story 가져오기
    @GetMapping("/view/{fromUserId}")
    public ResponseEntity<List<Story>> getStoriesFromUser(@PathVariable Long fromUserId,
                                                          @RequestParam(name = "date") String date) {
        try {
            Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            List<Story> userStories = storyService.findByUserIdAndDateGreaterThan(fromUserId, fromDate);
            return new ResponseEntity<>(userStories, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

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
}
