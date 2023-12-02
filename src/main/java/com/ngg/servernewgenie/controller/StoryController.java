package com.ngg.servernewgenie.controller;

import com.ngg.servernewgenie.config.BaseException;
import com.ngg.servernewgenie.config.BaseResponseStatus;
import com.ngg.servernewgenie.domain.CustomUserDetails;
import com.ngg.servernewgenie.domain.Story;
import com.ngg.servernewgenie.dto.ChatGptRequestDto;
import com.ngg.servernewgenie.dto.ChatGptResponseDto;
import com.ngg.servernewgenie.dto.Choice;
import com.ngg.servernewgenie.dto.QuestionRequestDto;
import com.ngg.servernewgenie.repository.PlantRepository;
import com.ngg.servernewgenie.service.ChatService;
import com.ngg.servernewgenie.service.StoryService;
import com.ngg.servernewgenie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(value = "/Story")
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;
    private final PlantRepository plantRepository;
    private final Map<String, String> replacementMap; // key-value 쌍을 저장하는 Map
    private final ChatService chatService; // ChatGPT와 상호 작용하는 서비스


    @Autowired
    public StoryController(
            StoryService storyService,
            UserService userService,
            PlantRepository plantRepository,
            @Qualifier("replacementMap") Map<String, String> replacementMap,ChatService chatService) {
        this.storyService = storyService;
        this.userService = userService;
        this.plantRepository = plantRepository;
        this.chatService = chatService;

        // 특정한 key-value 쌍을 Map에 추가하는 예시
        this.replacementMap = new HashMap<>();
        replacementMap.put("<Clusia>", "클루시아");
        replacementMap.put("<Fan-Palms>", "관음죽");
        replacementMap.put("<Lvicks plant>", "장미허브");
        replacementMap.put("<Pachira>", "파키라");
        replacementMap.put("<Wind orchid>", "풍란");
        replacementMap.put("<caladium>", "칼라디움");
        replacementMap.put("<carnation>", "카네이션");
        replacementMap.put("<creeping fig>", "푸밀라 고무나무");
        replacementMap.put("<croton>", "크로톤");
        replacementMap.put("<eucalyptus>", "유칼립투스");
        replacementMap.put("<freesia>", "프리지아");
        replacementMap.put("<geranium>", "제라늄");
        replacementMap.put("<poinsettia>", "포인세티아");
        replacementMap.put("<ribbon plant>", "접란");
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
    ////////////////////////
    private List<Map<String, String>> getMessages(QuestionRequestDto requestDto) {
        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", requestDto.getBody());
        messages.add(userMessage);

        return messages;
    }
    
    
    //
    @PostMapping("/explain/save/{storyId}")
    public ResponseEntity<String> saveStoryExplain(@PathVariable Long storyId, Authentication authentication) {
        String pltName = null;
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            System.out.println("!!!!! == pltName: " + pltName);

            // Map에서 특정 key에 해당하는 값이 있는지 확인하고, 있다면 대체값으로 교체
//            if (pltName != null && replacementMap.containsKey(pltName)) {
//                pltName = replacementMap.get(pltName);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant name not found or replacement not specified");
//            }
        }
        System.out.println("=============pltName: " + pltName);

        // 실제로 ChatGPT에게 요청을 보내어 설명을 얻는 로직
        String explanation = storyService.viewStoryExplain(storyId);
        //

        // pltName이 "No object detected"인 경우에 대한 예외 처리 코드
        if ("No object detected".equals(pltName)) {
            throw new BaseException(BaseResponseStatus.NO_OBJECT_DETECTES_ERROR, HttpStatus.BAD_REQUEST);
        }
        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("식물 <" + pltName + ">에 대해서 2줄 반 이내로 설명해줘."+
                "이때 식물 이름은 아래 예시를 참고해서 한국말로 말해줘." +
                "예를 들면 request: <Clusia>일때, 식물 이름: '클루시아'"+
                "request: <Fan-Palms>, 식물 이름: '관음죽'"+
                "request: <Lvicks plant>, 식물 이름: '장미허브'"+
                "request: <Pachira>, 식물 이름: '파키라'" +
                "request: <Wind orchid>, 식물 이름: '풍란'"+
                "request: <caladium>, 식물 이름: '칼라디움'"+
                "request: <carnation>, 식물 이름: '카네이션'" +
                "request: <creeping fig>, 식물 이름: '푸밀라 고무나무'"+
                "request: <croton>, 식물 이름: '크로톤'"+
                "request: <eucalyptus>, 식물 이름: '유칼립투스'" +
                "request: <freesia>, 식물 이름: '프리지아'"+
                "request: <geranium>, 식물 이름: '제라늄'"+
                "request: <poinsettia>, 식물 이름: '포인세티아'"+
                "request: <ribbon plant>, 식물 이름: '접란'");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        ChatGptResponseDto responseDto = chatService.askQuestion(requestDto);
        List<Choice> choices = responseDto.getChoices();

        //String explanation = null;

        if (choices != null && !choices.isEmpty()) {
            for (Choice choice : choices) {
                Choice.Message message = choice.getMessage();
                if (message != null) {
                    String content = message.getContent();
                    if (content != null) {
                        explanation = content;
                        break; // 설명을 찾으면 반복문 종료
                    }
                }
            }
        }

        if (explanation != null) {
            // 얻은 설명을 해당 storyId에 맞는 Story 테이블의 explain 필드에 저장
            storyService.saveStoryExplain(storyId, explanation);
            return ResponseEntity.ok("Explanation saved successfully for storyId: " + storyId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Explanation not found for plant: " + pltName);
        }
    }


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

    ///////////////////////////////////////////
    //
    @PostMapping("/music/save/{storyId}")
    public ResponseEntity<String> saveStoryMusic(@PathVariable Long storyId, Authentication authentication) {
        String pltName = null;
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            System.out.println("!!!!! == pltName: " + pltName);

            // Map에서 특정 key에 해당하는 값이 있는지 확인하고, 있다면 대체값으로 교체
//            if (pltName != null && replacementMap.containsKey(pltName)) {
//                pltName = replacementMap.get(pltName);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant name not found or replacement not specified");
//            }
        }
        System.out.println("=============pltName: " + pltName);

        // 실제로 ChatGPT에게 요청을 보내어 설명을 얻는 로직
        String explanation = storyService.viewStoryExplain(storyId);
        //

        // pltName이 "No object detected"인 경우에 대한 예외 처리 코드
        if ("No object detected".equals(pltName)) {
            throw new BaseException(BaseResponseStatus.NO_OBJECT_DETECTES_ERROR, HttpStatus.BAD_REQUEST);
        }
        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("Your answer form should be like this: <title>-'artist'. Don't say any other words except for this."+
                "Give me a song related to <" + pltName + ">. The song should have more than 1,000 hits on Youtube. " +
                "예를 들면 response: <Lemon tree>-'fools garden' 처럼 응답해줘.");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        ChatGptResponseDto responseDto = chatService.askQuestion(requestDto);
        List<Choice> choices = responseDto.getChoices();

        //String explanation = null;

        if (choices != null && !choices.isEmpty()) {
            for (Choice choice : choices) {
                Choice.Message message = choice.getMessage();
                if (message != null) {
                    String content = message.getContent();
                    if (content != null) {
                        explanation = content;
                        break; // 설명을 찾으면 반복문 종료
                    }
                }
            }
        }

        if (explanation != null) {
            // 얻은 설명을 해당 storyId에 맞는 Story 테이블의 music 필드에 저장
            storyService.saveStoryMusic(storyId, explanation);
            return ResponseEntity.ok("Music saved successfully for storyId: " + storyId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Music not found for plant: " + pltName);
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

    ///////////////////////////////////
    //
    @PostMapping("/poem/save/{storyId}")
    public ResponseEntity<String> saveStoryPoem(@PathVariable Long storyId, Authentication authentication) {
        String pltName = null;
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            System.out.println("!!!!! == pltName: " + pltName);

            // Map에서 특정 key에 해당하는 값이 있는지 확인하고, 있다면 대체값으로 교체
//            if (pltName != null && replacementMap.containsKey(pltName)) {
//                pltName = replacementMap.get(pltName);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant name not found or replacement not specified");
//            }
        }
        System.out.println("=============pltName: " + pltName);

        // 실제로 ChatGPT에게 요청을 보내어 설명을 얻는 로직
        String explanation = storyService.viewStoryExplain(storyId);
        //

        // pltName이 "No object detected"인 경우에 대한 예외 처리 코드
        if ("No object detected".equals(pltName)) {
            throw new BaseException(BaseResponseStatus.NO_OBJECT_DETECTES_ERROR, HttpStatus.BAD_REQUEST);
        }
        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("식물 <" + pltName + ">과 관련된 시를 짧게 창작해줘."+
                "이때 식물 이름은 아래 예시를 참고해서 한국말로 말해줘." +
                "예를 들면 request: <Clusia>일때, 식물 이름: '클루시아'"+
                "request: <Fan-Palms>, 식물 이름: '관음죽'"+
                "request: <Lvicks plant>, 식물 이름: '장미허브'"+
                "request: <Pachira>, 식물 이름: '파키라'" +
                "request: <Wind orchid>, 식물 이름: '풍란'"+
                "request: <caladium>, 식물 이름: '칼라디움'"+
                "request: <carnation>, 식물 이름: '카네이션'" +
                "request: <creeping fig>, 식물 이름: '푸밀라 고무나무'"+
                "request: <croton>, 식물 이름: '크로톤'"+
                "request: <eucalyptus>, 식물 이름: '유칼립투스'" +
                "request: <freesia>, 식물 이름: '프리지아'"+
                "request: <geranium>, 식물 이름: '제라늄'"+
                "request: <poinsettia>, 식물 이름: '포인세티아'"+
                "request: <ribbon plant>, 식물 이름: '접란'");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        ChatGptResponseDto responseDto = chatService.askQuestion(requestDto);
        List<Choice> choices = responseDto.getChoices();

        //String explanation = null;

        if (choices != null && !choices.isEmpty()) {
            for (Choice choice : choices) {
                Choice.Message message = choice.getMessage();
                if (message != null) {
                    String content = message.getContent();
                    if (content != null) {
                        explanation = content;
                        break; // 설명을 찾으면 반복문 종료
                    }
                }
            }
        }

        if (explanation != null) {
            // 얻은 설명을 해당 storyId에 맞는 Story 테이블의 music 필드에 저장
            storyService.saveStoryPoem(storyId, explanation);
            return ResponseEntity.ok("Poem saved successfully for storyId: " + storyId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poem not found for plant: " + pltName);
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


    ///////////////////////////////////
    //
    @PostMapping("/condition/save/{storyId}")
    public ResponseEntity<String> saveStoryCondition(@PathVariable Long storyId, Authentication authentication) {
        String pltName = null;
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getId();
            pltName = plantRepository.findMostRecentPltNameByUserId(userId);
            System.out.println("!!!!! == pltName: " + pltName);

            // Map에서 특정 key에 해당하는 값이 있는지 확인하고, 있다면 대체값으로 교체
//            if (pltName != null && replacementMap.containsKey(pltName)) {
//                pltName = replacementMap.get(pltName);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant name not found or replacement not specified");
//            }
        }
        System.out.println("=============pltName: " + pltName);

        // 실제로 ChatGPT에게 요청을 보내어 설명을 얻는 로직
        String explanation = storyService.viewStoryExplain(storyId);
        //

        // pltName이 "No object detected"인 경우에 대한 예외 처리 코드
        if ("No object detected".equals(pltName)) {
            throw new BaseException(BaseResponseStatus.NO_OBJECT_DETECTES_ERROR, HttpStatus.BAD_REQUEST);
        }
        QuestionRequestDto requestDto = new QuestionRequestDto();
        requestDto.setBody("Your answer form should start with this: '몇가지 조언을 드리겠습니다.'"+
                "식물 <" + pltName + ">를 키울 때 주의 할 점이나 조언을 2줄 반 이내로 해줘."+
                "번호 없이 구어체로 말해줘."+ "이때 식물 이름은 아래 예시를 참고해서 한국말로 말해줘." +
                "예를 들면 request: <Clusia>일때, 식물 이름: '클루시아'"+
                "request: <Fan-Palms>, 식물 이름: '관음죽'"+
                "request: <Lvicks plant>, 식물 이름: '장미허브'"+
                "request: <Pachira>, 식물 이름: '파키라'" +
                "request: <Wind orchid>, 식물 이름: '풍란'"+
                "request: <caladium>, 식물 이름: '칼라디움'"+
                "request: <carnation>, 식물 이름: '카네이션'" +
                "request: <creeping fig>, 식물 이름: '푸밀라 고무나무'"+
                "request: <croton>, 식물 이름: '크로톤'"+
                "request: <eucalyptus>, 식물 이름: '유칼립투스'" +
                "request: <freesia>, 식물 이름: '프리지아'"+
                "request: <geranium>, 식물 이름: '제라늄'"+
                "request: <poinsettia>, 식물 이름: '포인세티아'"+
                "request: <ribbon plant>, 식물 이름: '접란'");

        // 로그 출력
        System.out.println("=============Request Body: " + requestDto.getBody());

        ChatGptResponseDto responseDto = chatService.askQuestion(requestDto);
        List<Choice> choices = responseDto.getChoices();

        //String explanation = null;

        if (choices != null && !choices.isEmpty()) {
            for (Choice choice : choices) {
                Choice.Message message = choice.getMessage();
                if (message != null) {
                    String content = message.getContent();
                    if (content != null) {
                        explanation = content;
                        break; // 설명을 찾으면 반복문 종료
                    }
                }
            }
        }

        if (explanation != null) {
            // 얻은 설명을 해당 storyId에 맞는 Story 테이블의 music 필드에 저장
            storyService.saveStoryCondition(storyId, explanation);
            return ResponseEntity.ok("Condition saved successfully for storyId: " + storyId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condition not found for plant: " + pltName);
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
