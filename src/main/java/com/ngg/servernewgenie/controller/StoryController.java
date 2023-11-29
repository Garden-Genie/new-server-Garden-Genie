package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.service.StoryService;
import com.ngg.servernewgenie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/Story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    private final UserService userService;

    @Autowired
    public StoryController(UserService userService) {
        this.userService = userService;
    }

    // 1. 스토리 업로드
    @PostMapping("/upload/{storyId}")
    public ResponseEntity<String> updateUploadAttribute(@PathVariable Long storyId) {
        try {
            storyService.updateUploadByStoryId(storyId);
            return new ResponseEntity<>("Story uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 스토리 조회 (UserNum)
    @GetMapping("/view/user/{userNum}")
    public ResponseEntity<Object> viewUserStories(@PathVariable(name = "userNum") Long userNum) {
        try {
            List<Story> userStories = storyService.getUserStories(userNum);
            // 결과를 그대로 ResponseEntity에 설정
            return ResponseEntity.ok().body(userStories);
        } catch (Exception e) {
            // 에러가 발생한 경우 INTERNAL_SERVER_ERROR 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 3. 스토리 조회 (StoryId)
    @GetMapping("/view/story/{storyId}")
    public ResponseEntity<Story> viewStory(@PathVariable Long storyId) {
        try {
            Story story = storyService.getStory(storyId);
            return new ResponseEntity<>(story, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. 스토리 조회 (fromUserNum)
    @GetMapping("/view/following/{fromUserNum}")
    public ResponseEntity<List<Story>> viewFollowingStories(@PathVariable Long fromUserNum) {
        try {
            List<Story> followingStories = storyService.getFollowingStories(fromUserNum);
            return new ResponseEntity<>(followingStories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 5. 스토리 전체 조회
    @GetMapping("/allStory")
    public ResponseEntity<List<Story>> viewAllStories() {
        try {
            List<Story> allStories = storyService.getAllStories();
            return new ResponseEntity<>(allStories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 6. 스토리 삭제
    @DeleteMapping("/delete/{storyId}")
    public ResponseEntity<String> deleteStory(@PathVariable Long storyId) {
        try {
            storyService.deleteStory(storyId);
            return new ResponseEntity<>("Story deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // uz
    @PostMapping("/explain/save")
    public ResponseEntity<String> saveStoryExplain(@RequestHeader("storyId") Long storyId, @RequestBody String storyExplain) {
        storyService.saveStoryExplain(storyId, storyExplain); // body가 그대로 저장됨 (json으로 인식 못하는 듯)
        return ResponseEntity.ok("Explain saved successfully for storyId: " + storyId);
    }

    /*
    // header로 {storyId} 받는 방식
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
    */

    // Get Story/explain/view/{storyId}
    // {storyId} 스토리의 설명 가져오는 API
    @GetMapping("/explain/view/{storyId}")
    public ResponseEntity<String> viewStoryExplain(@PathVariable Long storyId) {
        String storyExplain = storyService.viewStoryExplain(storyId);
        if (storyExplain != null) {
            return ResponseEntity.ok(storyExplain);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Explain not found for storyId " + storyId);
        }
    }

    // Get Story/music/view/{storyId}
    // {storyId} 스토리의 추천 음악 가져오는 API
    @GetMapping("/music/view/{storyId}")
    public ResponseEntity<String> viewStoryMusic(@PathVariable Long storyId) {
        String storyMusic = storyService.viewStoryMusic(storyId);
        if (storyMusic != null) {
            return ResponseEntity.ok(storyMusic);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Music not found for storyId " + storyId);
        }
    }

    // Get Story/poem/view/{storyId}
    // {storyId} 스토리의 시 가져오는 API
    @GetMapping("/poem/view/{storyId}")
    public ResponseEntity<String> viewStoryPoem(@PathVariable Long storyId) {
        String storyPoem = storyService.viewStoryPoem(storyId);
        if (storyPoem != null) {
            return ResponseEntity.ok(storyPoem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Poem not found for storyId " + storyId);
        }
    }

    // Get Story/condition/view/{storyId}
    // {storyId} 스토리의 상태(조언) 가져오는 API
    @GetMapping("/condition/view/{storyId}")
    public ResponseEntity<String> viewStoryCondition(@PathVariable Long storyId) {
        String storyCondition = storyService.viewStoryCondition(storyId);
        if (storyCondition != null) {
            return ResponseEntity.ok(storyCondition);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Condition not found for storyId " + storyId);
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
